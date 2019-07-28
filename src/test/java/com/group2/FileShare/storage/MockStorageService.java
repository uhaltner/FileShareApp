package com.group2.FileShare.storage;

import java.io.File;
import java.util.HashMap;

public class MockStorageService implements IStorage {

	private String mockDownloadLinkRoot;
	private HashMap<String, File> mockStorage;
	static MockStorageService service;
	
	private MockStorageService() {
		mockDownloadLinkRoot = "https://fileshare-special-storage.com/";
		mockStorage = new HashMap<String, File>();
	}
	
	public static MockStorageService getInstance() {
		if (service == null) {
			service = new MockStorageService();
		}
		return service;
	}

	public boolean deleteFile(String filePath) {
		return mockStorage.remove(filePath) != null;
	}

	public String downloadFile(String filePath) {
		String downloadLink = null;
		if (mockStorage.containsKey(filePath)) {
			downloadLink = mockDownloadLinkRoot + filePath;
		}
		return downloadLink;
	}

	public boolean uploadFile(File file, String filename) {
		mockStorage.put(filename, file);
		return true;
	}

}
