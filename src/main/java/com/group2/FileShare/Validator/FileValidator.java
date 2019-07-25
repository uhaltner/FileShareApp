package com.group2.FileShare.Validator;

import org.springframework.web.multipart.MultipartFile;

import com.group2.FileShare.DefaultProperties;

public class FileValidator implements IValidator {
	private String[] invalidMimePrefixes;
	private Long sizeLimitInBytes;
	
	public FileValidator() {
		invalidMimePrefixes = DefaultProperties.getInstance().getInvalidMimePrefixes();
		sizeLimitInBytes = DefaultProperties.getInstance().getSizeLimitInBytes();
		
	}
	
	private boolean isInvalidFileType(String mimeType) {
		for (String type : invalidMimePrefixes) {
			if (mimeType.startsWith(type)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAboveFileSizeLimit(Long size) {
		if (size > sizeLimitInBytes) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean validate(Object obj) throws Exception {
		MultipartFile file = (MultipartFile) obj;
		String mimeType = file.getContentType();
		Long fileSize = file.getSize();

		if (isInvalidFileType(mimeType)) {
			throw new Exception("File Upload failed: Invalid file type selected (Videos Not Allowed).");
		}

		if (isAboveFileSizeLimit(fileSize)) {
			throw new Exception("File too large please upload a file with a size no greater than "
					+ Math.round(((double) sizeLimitInBytes) / 1000000 * 10) / 10.0 + " MB.");
		}
		return true;
	}
	
}
