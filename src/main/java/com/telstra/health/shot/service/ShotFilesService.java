package com.telstra.health.shot.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ShotFilesService {

	void saveUserManual(MultipartFile file);
	
	void saveUserMatrix(MultipartFile file);
	
	Resource loadUserManual();
	
	Resource loadUserStabilityMatrix();
}
