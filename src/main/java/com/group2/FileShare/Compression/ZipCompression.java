package com.group2.FileShare.Compression;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompression implements ICompression
{
	private static final Logger logger = LogManager.getLogger(ZipCompression.class);
	private String extension = ".zip";
	
	@Override
	public File compressFile(MultipartFile file)
	{
		try
		{
			String fileName = file.getOriginalFilename();
			File compressedFile = File.createTempFile(fileName, extension);
			FileOutputStream fileOutputStream = new FileOutputStream(compressedFile);
	        InputStream fileInputStream = file.getInputStream();

		
	        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
	        ZipEntry zipEntry = new ZipEntry(fileName);
	        zipOutputStream.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;

	        while((length = fileInputStream.read(bytes)) >= 0)
	        {
	        	zipOutputStream.write(bytes, 0, length);
	        }
	        
	        zipOutputStream.close();
	        fileInputStream.close();
	        fileOutputStream.close();
	        
	        return compressedFile;
	
		}
		catch (FileNotFoundException e)
		{
			logger.log(Level.ERROR, "Failed to compress file at compressFile(): ", e);
		}
		catch (IOException ex)
		{
			logger.log(Level.ERROR, "Failed to compress file at compressFile(): ", ex);
		}
		
		return null;
	}
	
	@Override
	public File deCompressFile(String fileZip) {
		return null;
	}
}
