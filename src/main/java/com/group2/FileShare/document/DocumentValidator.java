package com.group2.FileShare.document;

import java.util.List;

public abstract class DocumentValidator {
	private List<FileType> validFileTypes;
	protected abstract void loadValidFileTypes();
	protected abstract boolean hasValidFileType();
	protected abstract boolean hasValidFileSize();
	
}
