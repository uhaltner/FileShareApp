package com.group2.FileShare.document;

import java.util.Date;

public class Document {
	private int id;
	private String name;
	private int size;
	private String description;
	private String storageURL;
	private int ownerId; //changed this
	private FileType type;
	private Date createdDate;
	private boolean isPinned; //??? Should this be here?
	private boolean isPublic;
	private boolean isTrashed;
	private Date trashedDate;
	
	public Document() {
		super();
	}
	
	public Document(int id, String name, int size, String storageURL, int ownerId, FileType type) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.storageURL = storageURL;
		this.ownerId = ownerId;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
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

	public void setStorageURL(String storageURL) {
		this.storageURL = storageURL;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
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
		return "Document [id=" + id + ", name=" + name + ", size=" + size + ", description=" + description
				+ ", storageURL=" + storageURL + ", ownerId=" + ownerId + ", type=" + type + ", createdDate="
				+ createdDate + ", isPinned=" + isPinned + ", isPublic=" + isPublic + ", isTrashed=" + isTrashed
				+ ", trashedDate=" + trashedDate + "]";
	}
	
	
	
}
