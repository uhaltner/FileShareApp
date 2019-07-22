package com.group2.FileShare.Dashboard;

public class SearchBarHandler {

	private boolean searchRequired;
	private String searchPhrase;

	public SearchBarHandler(){
		reset();
	}

	public boolean isSearchRequired() {
		return searchRequired;
	}

	public void setSearchRequired(boolean searchRequired) {
		this.searchRequired = searchRequired;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void reset(){
		this.searchRequired = false;
		this.searchPhrase = "";
	}
}
