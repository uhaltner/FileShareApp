package com.group2.FileShare.Validator;

import org.springframework.web.multipart.MultipartFile;

import com.group2.FileShare.DefaultProperties;
import com.group2.FileShare.document.DocumentDAO;
import com.group2.FileShare.document.IDocumentDAO;

public class StorageLimitValidator implements IValidator {
	private IDocumentDAO documentDAO;
	private Long storageSizeLimitInBytes;
	
	
	public StorageLimitValidator() {
		storageSizeLimitInBytes = DefaultProperties.getInstance().getStorageSizeLimitInBytes();
		documentDAO = new DocumentDAO();
	}
	
	private boolean storageIsNotAvailable(Long size) {
		long totalFilesize = documentDAO.getTotalFileSize();
		if ((totalFilesize + size) > storageSizeLimitInBytes) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean validate(Object obj) throws Exception {
		MultipartFile file = (MultipartFile) obj;
		Long fileSize = file.getSize();

		if (storageIsNotAvailable(fileSize)) {
			throw new Exception("Insufficient Storage Capacity. Upload will exceed your storage capacity limit of "
					+ Math.round(((double) storageSizeLimitInBytes) / 1000000000 * 10) / 10.0 + " GB.");
		}
		return true;
	}
	
}
