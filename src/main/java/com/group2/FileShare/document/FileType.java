package com.group2.FileShare.document;

import java.util.ArrayList;
import java.util.List;

public class FileType {
	private int id;
	private String name;
	private List<String> extensions;
	
	public FileType() {
		super();
	}
	
	public FileType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.extensions = new ArrayList<>();
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

	public List<String> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<String> extensions) {
		this.extensions = extensions;
	}
	
	public void addExtension(String extension) {
		this.extensions.add(extension);
	}

	@Override
	public String toString() {
		return "FileType [id=" + id + ", name=" + name + ", extensions=" + extensions + "]";
	}
	
	
}
