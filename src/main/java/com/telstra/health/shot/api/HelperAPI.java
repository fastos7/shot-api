/*
 * This is a helper API implementation. This is not used anywhere in actual application.
 * It is used for automating the testing.
 */

package com.telstra.health.shot.api;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helper")
public class HelperAPI {

	private static final Logger logger = LoggerFactory.getLogger(HelperAPI.class);

	/*
	 * @Input newDateTime: string in the format MMddhhmm[[yy]yy]
	 */
	@PostMapping("/systemdate")
	public void changeSystemDate(@RequestBody String newDateTime) {
		logger.info("Input newDateTime: [{}]", newDateTime);
		try {
			Runtime.getRuntime().exec("date " + newDateTime);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return;
	}
}
