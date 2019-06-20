package com.group2.FileShare.Compression;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface ICompression {
	public File compressFile(MultipartFile file);
	public File deCompressFile(String url);
	public File discard(String path);
}
