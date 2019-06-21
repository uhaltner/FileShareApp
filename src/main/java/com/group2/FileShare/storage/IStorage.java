package com.group2.FileShare.storage;

import java.io.File;

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
	 * @param file - A java.io.File to be uploaded.
	 * @param filename - Name to store the file as.
	 * @return True if successful and False otherwise
	 */
	boolean uploadFile(File file, String filename);
}
