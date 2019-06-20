package com.group2.FileShare.Compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class ZipCompression implements ICompression {

	private String sysDirectory = System.getProperty("user.dir") + "/";
	private String extension = ".zip";
	
	@Override
	public File compressFile(MultipartFile file) {
		
		try {
			String fileName = file.getOriginalFilename();
			String ziFileName = fileName  + extension;
			FileOutputStream fileOutputStream = new FileOutputStream(ziFileName);
	        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
		
	        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
	        ZipEntry zipEntry = new ZipEntry(fileName);
	        zipOutputStream.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;
	        while((length = fileInputStream.read(bytes)) >= 0) {
	        	zipOutputStream.write(bytes, 0, length);
	        }
	        
	        zipOutputStream.close();
	        fileInputStream.close();
	        fileOutputStream.close();
	        
	        File compressedFile = new File(sysDirectory + ziFileName);
	        return compressedFile;
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public File deCompressFile(String fileZip) {
		return null;
	}
}
