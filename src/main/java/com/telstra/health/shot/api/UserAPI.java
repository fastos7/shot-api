package com.telstra.health.shot.api;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.api.exception.UserAPIException;
import com.telstra.health.shot.dto.UserDTO;
import com.telstra.health.shot.dto.UserSearchDTO;
import com.telstra.health.shot.service.UserService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RestController
@RequestMapping("/api/user/")
public class UserAPI {
	Logger logger = LoggerFactory.getLogger(UserAPI.class);

	@Autowired
	private UserService userService;

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		try {
			// Save the User instance as well as assign authorities to Customer sites.
			userDTO = this.userService.createUserAccountAndAuthorities(userDTO);

			// Upon successful User creation, return Location Header
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(userDTO.getUserId()).toUri();
			return ResponseEntity.created(location).body(userDTO);
		} catch (NameAlreadyBoundException | BadCredentialsException ex) {
			throw new UserAPIException(ex.getMessage(), HttpStatus.FORBIDDEN, ex);
		} catch (Exception ex) {
			throw new ApiException(ex.getMessage());
		}
	}

	@PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
		try {
			userDTO.setUserId(userId);

			// Save the User instance as well as assign authorities to Customer sites.
			this.userService.updateUserAccountAndAuthorities(userDTO);

		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage(), ex.getErrorCode());
		} catch (Exception ex) {
			throw new ApiException(ex.getMessage());
		}
	}

	@PutMapping(value = "/{userId}/password/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUserPassword(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
		try {
			// Save the User instance as well as assign authorities to Customer sites.
			this.userService.updateUserPassword(userDTO.getEmail(), userDTO.getPassword());

		} catch (Exception ex) {
			throw new ApiException(ex.getMessage());
		}
	}

	@DeleteMapping(value = "/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
		userDTO.setUserId(userId);
		this.userService.deleteUserAccount(userDTO);
	}

	@GetMapping(value = "/")
	@ResponseStatus(HttpStatus.OK)
	public UserSearchDTO searchUsers(UserSearchDTO userSearchDTO) {
		return this.userService.searchUsers(userSearchDTO);
	}
}
