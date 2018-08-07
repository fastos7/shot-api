package com.telstra.health.shot.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.telstra.health.shot.dto.NewsFeedDTO;
import com.telstra.health.shot.service.NewsFeedService;

@RestController
@RequestMapping("/api/siteAdmin/newsFeed/")
public class NewsFeedAPI {

	@Autowired
	private NewsFeedService newsFeedService;

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<NewsFeedDTO> createNewsFeed(@RequestBody NewsFeedDTO newsFeedDTO) {
		this.newsFeedService.createNewsFeed(newsFeedDTO);

		// Upon successful NewsFeed creation, return Location Header
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newsFeedDTO.getId()).toUri();
		return ResponseEntity.created(location).body(newsFeedDTO);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<NewsFeedDTO> updateNewsFeed(@PathVariable Long id, @RequestBody NewsFeedDTO newsFeedDTO) {
		this.newsFeedService.updateNewsFeed(newsFeedDTO);

		return ResponseEntity.ok().body(newsFeedDTO);
	}

	@GetMapping("/")
	@ResponseBody
	public List < NewsFeedDTO > getAllNewsFeed() {
		return this.newsFeedService.getAllNewsFeeds();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public NewsFeedDTO getNewsFeed(@PathVariable("id") Long id) {
		return this.newsFeedService.getNewsFeed(id);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNewsFeed(@PathVariable Long id) {
		this.newsFeedService.deleteNewsFeed(id);

	}

}
