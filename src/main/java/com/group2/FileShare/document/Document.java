package com.group2.FileShare.document;

import java.util.Date;

public class Document {
	private int id;
	private String filename;
	private long size;
	private String description;
	private int ownerId;
	private Date createdDate;
	private boolean isPinned;
	private boolean isPublic;
	private boolean isTrashed;
	private Date trashedDate;
	private String storageURL;

	public Document() {
		super();
		this.createdDate = new Date();
	}

	public Document(int id, String filename, int size, String storageURL, int ownerId) {
		super();
		this.id = id;
		this.filename = filename;
		this.size = size;
		this.storageURL = storageURL;
		this.ownerId = ownerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String name) {
		this.filename = name;
	}

	public long getSize() {
		return size;
	}
	

	public String getSizeString() {
		if (size < 1000) {
			return (""+size+" bytes");
		}else if (size < 1000000) {
			return (""+ Math.round(size/1000 * 10)/10 +" KB");
		}else if (size < 1000000000) {
			return (""+ Math.round(size/1000000 * 10)/10 +" MB");
		}else {
			return (""+ Math.round(size/1000000000 * 10)/10 +" GB");
		}
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStorageURL() {
		return storageURL;
	}

	public void setStorageURL() {
		this.storageURL = (Math.random() * 10 * Math.random() * ownerId) + ((filename.toLowerCase()).replaceAll(" ", "")) + createdDate.getTime();
	}
	
	public void setStorageURL(String storageURL) {
		this.storageURL = storageURL;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isTrashed() {
		return isTrashed;
	}

	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	public Date getTrashedDate() {
		return trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", name=" + filename + ", size=" + size + ", description=" + description
				+ ", storageURL=" + storageURL + ", ownerId=" + ownerId + ", createdDate=" + createdDate + ", isPinned="
				+ isPinned + ", isPublic=" + isPublic + ", isTrashed=" + isTrashed + ", trashedDate=" + trashedDate
				+ "]";
	}

}
