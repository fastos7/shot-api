package com.telstra.health.shot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telstra.health.shot.entity.NewsFeed;

public interface NewsFeedDAO extends JpaRepository<NewsFeed, Long> {

	/**
	 * Find list of News Feed instancs sorted by Updated Date in descending order
	 * @return list of News Feed instances.
	 */
	List < NewsFeed > findAllByOrderByUpdatedDateDesc();
	
	/**
	 * Find News Feed by Id.
	 * @param newsFeedId
	 * @return NewsFeed instance.
	 */
	NewsFeed findById(Long newsFeedId);
}
