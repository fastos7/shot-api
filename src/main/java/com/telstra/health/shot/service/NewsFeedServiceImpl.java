package com.telstra.health.shot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telstra.health.shot.dao.NewsFeedDAO;
import com.telstra.health.shot.dto.NewsFeedDTO;
import com.telstra.health.shot.entity.NewsFeed;
import com.telstra.health.shot.util.ShotUtils;

/**
 * Implementation class of Service that implements CRUD operations on SHOT News Feeds.
 * @author osama.shakeel
 *
 */
@Service
@Transactional
public class NewsFeedServiceImpl implements NewsFeedService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewsFeedDAO newsFeedDAO;

	/**
	 * Return all News Feeds currently configured in SHOT
	 * @return News Feed DTO instances.
	 */
	@Override
	public List < NewsFeedDTO > getAllNewsFeeds() {
		// Find all News Feed in descending order of creation and updation
		List <NewsFeed> newsFeeds = this.newsFeedDAO.findAllByOrderByUpdatedDateDesc();
		List <NewsFeedDTO> newsFeedDTOs = null;
		
		// Create list of DTO instances of the returned News Feeds.
		if (newsFeeds != null && !newsFeeds.isEmpty()) {
			newsFeedDTOs = newsFeeds.stream().map(newsFeed -> {
				NewsFeedDTO newsFeedDTO = 
						new NewsFeedDTO(newsFeed.getId(), newsFeed.getHeader(), newsFeed.getPostedBy(),
								newsFeed.getShortDesc(), newsFeed.getLongDesc(),
								ShotUtils.formatDateToJS(newsFeed.getUpdatedDate() != null? newsFeed.getUpdatedDate() : newsFeed.getCreatedDate()));
				return newsFeedDTO;
			}).collect(Collectors.toList());
		}
		logger.info("{} News Feed instances found", newsFeedDTOs != null? newsFeedDTOs.size() : 0);
		return newsFeedDTOs != null? newsFeedDTOs : new ArrayList<>();
	}

	/**
	 * Return News Feed instance identified by the given Id.
	 * @param newsFeedId Id of News Feed.
	 * @return News Feed DTO instance if found, else null.
	 */
	@Override
	public NewsFeedDTO getNewsFeed(Long newsFeedId) {
		// Find the News Feed by Id and then create its DTO instance.
		NewsFeed newsFeed = this.newsFeedDAO.findById(newsFeedId);
		if (newsFeed != null) {
			logger.info("News Feed with Id: {}, and Posted By: {} found", newsFeedId, newsFeed.getPostedBy());
			return new NewsFeedDTO(newsFeed.getId(), newsFeed.getHeader(), newsFeed.getPostedBy(),
					newsFeed.getShortDesc(), newsFeed.getLongDesc(),
					ShotUtils.formatDateToJS(newsFeed.getUpdatedDate()));
		}
		logger.info("No News Feed with Id: {} found", newsFeedId);
		return null;
	}

	/**
	 * Create and save the given News Feed represented by the DTO.
	 * @param newsFeedDTO News Feed instance to create.
	 * @return Newly created DTO instance of the News Feed.
	 */
	@Override
	public NewsFeedDTO createNewsFeed(NewsFeedDTO newsFeedDTO) {
		// Create and populate the new News Feed instance
		NewsFeed newsFeed = new NewsFeed();
		newsFeed.setHeader(newsFeedDTO.getHeader());
		newsFeed.setPostedBy(newsFeedDTO.getPostedBy());

		String shortDesc = ShotUtils.truncateString(newsFeedDTO.getShortDesc(), 200);
		newsFeed.setShortDesc(shortDesc);
		String longDesc = ShotUtils.truncateString(newsFeedDTO.getLongDesc(), 3000);
		newsFeed.setLongDesc(longDesc);

		Date postDate = new Date();
		String loginUser = ShotUtils.getLoginUserKey();
		newsFeed.setCreatedDate(postDate);
		newsFeed.setCreatedBy(loginUser);
		newsFeed.setIsActive(Boolean.TRUE);

		// Save the new News Feed instance
		this.newsFeedDAO.save(newsFeed);
		newsFeedDTO.setId(newsFeed.getId());
		newsFeedDTO.setPublishDate(ShotUtils.formatDateToJS(postDate));
		newsFeedDTO.setShortDesc(shortDesc);
		newsFeedDTO.setLongDesc(longDesc);

		logger.info("New News Feed with Id: {}, Posted By: {} created", newsFeed.getId(), newsFeed.getPostedBy());
		return newsFeedDTO;
	}
	
	/**
	 * Update and save the given News Feed with updated info in the given DTO.
	 * @param newsFeedDTO News Feed instance to create.
	 * @return Newly created DTO instance of the News Feed.
	 */
	@Override
	public NewsFeedDTO updateNewsFeed(NewsFeedDTO newsFeedDTO) {
		NewsFeed newsFeed = this.newsFeedDAO.findById(newsFeedDTO.getId());
		
		newsFeed.setHeader(newsFeedDTO.getHeader());
		newsFeed.setPostedBy(newsFeedDTO.getPostedBy());
		String shortDesc = ShotUtils.truncateString(newsFeedDTO.getShortDesc(), 200);
		newsFeed.setShortDesc(shortDesc);
		String longDesc = ShotUtils.truncateString(newsFeedDTO.getLongDesc(), 3000);
		newsFeed.setLongDesc(longDesc);

		Date postDate = new Date();
		String loginUser = ShotUtils.getLoginUserKey();
		newsFeed.setCreatedBy(loginUser);
		newsFeed.setUpdatedDate(postDate);
		newsFeed.setUpdatedBy(loginUser);
		newsFeed.setIsActive(Boolean.TRUE);

		this.newsFeedDAO.save(newsFeed);
		newsFeedDTO.setPublishDate(ShotUtils.formatDateToJS(postDate));
		newsFeedDTO.setShortDesc(shortDesc);
		newsFeedDTO.setLongDesc(longDesc);

		logger.info("News Feed with Id: {}, Posted By: {} updated", newsFeed.getId(), newsFeed.getPostedBy());
		return newsFeedDTO;
	}
	
	/**
	 * Delete the given NewsFeed identified by Id.
	 * @param Id of the News Feed to delete.
	 */
	@Override
	public void deleteNewsFeed(Long id) {
		this.newsFeedDAO.delete(id);
		logger.info("News Feed with Id: {} deleted", id);
	}
}
