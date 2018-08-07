package com.telstra.health.shot.service.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.telstra.health.shot.service.UserService;

/**
 * Represents a scheduler that runs every midnight to de-activate Users not loggedin for more than the disableUserTime amount of time.
 * @author osama.shakeel
 *
 */
@Component
public class UserScheduler {

	private static Logger logger = LoggerFactory.getLogger(UserScheduler.class);
	
	/**
	 * Amount of time in days to de-activate Users.
	 */
	@Value("${user.disabe.time.period}")
	private int disableUserTime;
	
	@Autowired
	private UserService userService;

	/**
	 * Main scheduler method used to de-activate Users that have not 
	 * loggedin for disableUserTime amount of time in days.
	 */
	@Scheduled(cron="${user.disabe.cron.expr}")
	public void disableInactiveUsers() {
		logger.info("De-activating Users who have not loggedin for {} days", this.disableUserTime);

		// First get the list of User's Ids that have not logged in for more than disableUserTime (90 days)
		List < Long > userIds = this.userService.getActiveUsersToDeActivate(disableUserTime);

		if (!CollectionUtils.isEmpty(userIds)) {
			logger.info("{} Users found who have not loggedin for {} days", userIds.size(), this.disableUserTime);			
			for (Long userId: userIds) {
				try {
					// De-activate the User
					this.userService.deActivateUser(userId);
				} catch (Exception ex) {
					// User could not de-activated. Move on to the other Users in the list
					logger.error("User with Id {} could not be de-actvated", userId);
					logger.error("", ex);			
				}
			}
		} else {
			logger.info("No User found who has not loggedin for {} days", this.disableUserTime);
		}
	}
}
