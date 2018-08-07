package com.telstra.health.shot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.service.ShotFilesService;

@RestController
@RequestMapping("/api/siteAdmin/userFiles/")
public class UserFilesAPI {

	@Autowired
	private ShotFilesService shotFilesService;

	@PostMapping("/userManual/")
	@ResponseStatus(HttpStatus.OK)
	public void uploadUserManual(@RequestPart("file") MultipartFile manualFile) {
		try {
			shotFilesService.saveUserManual(manualFile);
		} catch (Exception e) {
			throw new ApiException("Error occurred in saving User Manual");
		}
	}

	@PostMapping("/stabilityMatrix/")
	@ResponseStatus(HttpStatus.OK)
	public void uploadUserMatrix(@RequestPart("file") MultipartFile matrixFile) {
		try {
			shotFilesService.saveUserMatrix(matrixFile);
		} catch (Exception e) {
			throw new ApiException("Error occurred in saving User Stability Matrix");
		}
	}

	@GetMapping("/userManual/")
	@ResponseBody
	public ResponseEntity<Resource> downloadUserManual() {
		Resource file = shotFilesService.loadUserManual();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.header(HttpHeaders.CONTENT_TYPE, "application/pdf")
				.body(file);
	}

	@GetMapping("/stabilityMatrix/")
	@ResponseBody
	public ResponseEntity<Resource> downloadUserMatrix() {
		Resource file = shotFilesService.loadUserStabilityMatrix();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.header(HttpHeaders.CONTENT_TYPE, "application/pdf")
				.body(file);
	}

}
