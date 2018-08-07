package com.telstra.health.shot.service;

import java.util.List;

import com.telstra.health.shot.dto.NewsFeedDTO;

public interface NewsFeedService {

	List < NewsFeedDTO > getAllNewsFeeds();
	
	NewsFeedDTO getNewsFeed(Long newsFeedId);
	
	NewsFeedDTO createNewsFeed(NewsFeedDTO newsFeedDTO);
	
	NewsFeedDTO updateNewsFeed(NewsFeedDTO newsFeedDTO);
	
	void deleteNewsFeed(Long id);
}
