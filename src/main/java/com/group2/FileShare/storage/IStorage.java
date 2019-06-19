package com.group2.FileShare.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IStorage {
	/**
	 * @param filePath - The path of the file to be deleted
	 * @return True if successful and False otherwise
	 */
	boolean deleteFile(String filePath);
	/**
	 * @param filePath - The path of the file to be downloaded
	 * @return downloadFile URL String
	 */
	String downloadFile(String filePath);
	/**
	 * @param file - A representation of an uploaded file received in a multipart request.
	 * @param filename - Name to store the file as.
	 * @return True if successful and False otherwise
	 */
	boolean uploadFile(MultipartFile file, String filename);
}
