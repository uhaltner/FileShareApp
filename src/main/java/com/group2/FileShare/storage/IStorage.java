package com.group2.FileShare.storage;

import org.springframework.web.multipart.MultipartFile;

public interface IStorage {
	String downloadFile(MultipartFile file);
	String uploadFile(MultipartFile file);
}
