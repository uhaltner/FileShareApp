package com.group2.FileShare.Compression;

import java.io.File;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

public interface ICompression {
	public File compressFile(MultipartFile file);
	public File deCompressFile(URL url);
	String getExtention();
}
