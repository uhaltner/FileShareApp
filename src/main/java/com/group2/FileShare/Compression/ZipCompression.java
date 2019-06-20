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

	@Override
	public File compressFile(MultipartFile file) {
		
        FileOutputStream fos;
		try {
			String fileName = file.getOriginalFilename();
			fos = new FileOutputStream(fileName + ".zip");
	        FileInputStream fis = (FileInputStream) file.getInputStream();

	        ZipOutputStream zipOut = new ZipOutputStream(fos);
	        ZipEntry zipEntry = new ZipEntry(fileName);
	        zipOut.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;
	        while((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	        }
	        
	        zipOut.close();
	        fis.close();
	        fos.close();
	        
	        return new File(System.getProperty("user.dir") + file.getOriginalFilename());
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public File deCompressFile(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File discard(String path) {
		// TODO Auto-generated method stub
		return null;
	}
}
