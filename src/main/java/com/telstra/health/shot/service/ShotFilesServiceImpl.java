package com.telstra.health.shot.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.telstra.health.shot.service.exception.ShotServiceException;

/**
 * Service implementation class responsible for uploading and downloading SHOT Utility files:
 * User Manual and Stability Matrix documents.
 * @author osama.shakeel
 *
 */

@Service
public class ShotFilesServiceImpl implements ShotFilesService {
	private static Logger logger = LoggerFactory.getLogger(ShotFilesServiceImpl.class);

	@Value("${user.manual.file.path}")
	private String userManualFilePath;

	@Value("${user.matrix.file.path}")
	private String stabilityMatrixFilePath;

	@PostConstruct
	public void init() throws IOException {
		StringBuilder error = new StringBuilder();
		File userManualFile = Paths.get(this.userManualFilePath).toFile();
		if (!userManualFile.exists()) {
			String errorMessage = String.format("User Manual file at location %s does not exist", userManualFile);
			error.append(errorMessage).append(", ");
			logger.error(errorMessage);
		}
		File userMatrixFile = Paths.get(this.stabilityMatrixFilePath).toFile();
		if (!userMatrixFile.exists()) {
			String errorMessage = String.format("Stability Matrix file at location %s does not exist", stabilityMatrixFilePath);
			error.append(errorMessage);
			logger.error(errorMessage);
		}
		if (error.length() > 0) {
			throw new ShotServiceException(error.toString());
		}
	}

	/**
	 * Saves the new User Manual that is uploaded by the User.
	 * @param file New User Manual file to be saved.
	 */
	public void saveUserManual(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), Paths.get(this.userManualFilePath), StandardCopyOption.REPLACE_EXISTING);
			logger.info("New User Manual was successfully uploaded and saved");
		} catch (Exception ex) {
			logger.error("Error occurred in saving User Manual", ex);
			throw new RuntimeException("Error occurred in saving User Manual", ex);
		}
	}

	/**
	 * Saves the new Stability Matrix that is uploaded by the User.
	 * @param file New Stability Matrix file to be saved.
	 */
	public void saveUserMatrix(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), Paths.get(this.stabilityMatrixFilePath), StandardCopyOption.REPLACE_EXISTING);
			logger.info("New Stability Matrix was successfully uploaded and saved");
		} catch (Exception ex) {
			logger.error("Error occurred in saving Stability Matrix", ex);
			throw new RuntimeException("Error occurred in saving User Stability Matrix", ex);
		}
	}

	/**
	 * Load the User Manual document.
	 * @return User Manual file.
	 */
	public Resource loadUserManual() {
		try {
			return this.loadResource(this.userManualFilePath);
		} catch (FileNotFoundException ex) {
			logger.error("User Manual does not exist");
			throw new RuntimeException("User Manual does not exist");
		} catch (Exception ex) {
			logger.error("Error occurred in loading User Manual", ex);
			throw new RuntimeException("Error occurred in loading User Manual", ex);
		}
	}

	/**
	 * Load the User Manual document.
	 * @return User Manual file.
	 */
	public Resource loadUserStabilityMatrix() {
		try {
			return this.loadResource(this.stabilityMatrixFilePath);
		} catch (FileNotFoundException ex) {
			logger.error("Stability Matrix does not exist");
			throw new RuntimeException("Stability Matrix does not exist");
		} catch (Exception ex) {
			logger.error("Error occurred in loading Stability Matrix", ex);
			throw new RuntimeException("Error occurred in loading Stability Matrix", ex);
		}
	}

	/**
	 * Load the file located at the given filepath
	 * @param filePath path to the load resource from
	 * @return Resource instance of the file.
	 * @throws Exception
	 */
	private Resource loadResource(String filePath) throws Exception {
		Path file = Paths.get(filePath);
		Resource resource = new UrlResource(file.toUri());
		if (resource.exists() || resource.isReadable()) {
			return resource;
		} else {
			throw new FileNotFoundException();
		}
	}

}
