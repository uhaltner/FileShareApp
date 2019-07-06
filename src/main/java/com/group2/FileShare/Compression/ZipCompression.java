package com.group2.FileShare.Compression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.web.multipart.MultipartFile;


public class ZipCompression implements ICompression {

	private static String extension  = ".zip";
	
	@Override
	public File compressFile(MultipartFile file) {
		
		try {
			String fileName = file.getOriginalFilename();
			File compressedFile = File.createTempFile(fileName, extension);
			FileOutputStream fileOutputStream = new FileOutputStream(compressedFile);
	        InputStream fileInputStream = file.getInputStream();

		
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
	        
	        return compressedFile;
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public File deCompressFile(String fileURL) {

		ZipInputStream zis;
		try {
			URL url = new URL(fileURL );
			byte[] buffer = new byte[1024];

			zis = new ZipInputStream(url.openStream());
			ZipEntry zipEntry = zis.getNextEntry();
			String tempDir = System.getProperty("java.io.tmpdir");

			while (zipEntry != null) {

				File newFile =   new File(tempDir , zipEntry.getName());  //File.createTempFile("",zipEntry.getName());
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
				return newFile;
			}
			zis.closeEntry();
			zis.close();

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}
