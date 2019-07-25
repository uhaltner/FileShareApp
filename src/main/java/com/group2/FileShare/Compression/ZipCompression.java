package com.group2.FileShare.Compression;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
	public File deCompressFile(URL url) 
	{
		ZipInputStream zis;
		try 
		{
			byte[] buffer = new byte[1024];

			zis = new ZipInputStream(url.openStream());
			ZipEntry zipEntry = zis.getNextEntry();
			String tempDir = System.getProperty("java.io.tmpdir");

			while (zipEntry != null) 
			{
				File newFile =   new File(tempDir , zipEntry.getName());
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) 
				{
					fos.write(buffer, 0, len);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
				return newFile;
			}
			zis.closeEntry();
			zis.close();

		} 
		catch (FileNotFoundException e) 
		{
			logger.log(Level.ERROR, "File not found exception at deCompressFile(): ", e.getMessage());
		} catch (IOException e) 
		{
			logger.log(Level.ERROR, "IOException at deCompressFile(): ", e.getMessage());
		}

		return null;
	}

	@Override
	public String getExtention() {
		return extension;
	}
}
