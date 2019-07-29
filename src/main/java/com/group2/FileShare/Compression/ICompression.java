package com.group2.FileShare.Compression;

import java.io.File;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

public interface ICompression
{
	File compressFile(MultipartFile file);
	File deCompressFile(URL url);
	String getExtension();
}
