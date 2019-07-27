package com.group2.FileShare.Dashboard.MockObjects;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.IDocumentDAO;
import com.group2.FileShare.document.SharedDocumentLink;

import java.util.ArrayList;
import java.util.List;

public class DocumentDAOMock implements IDocumentDAO {

	@Override
	public List<Document> getDocumentList(String query, int userId, boolean publicDocumentsOnly, boolean trashedDocumentsOnly) {

		List<Document> list = new ArrayList<>();

		Document docA = new Document();
		docA.setFilename("FileName.txt");

		list.add(docA);

		return list;
	}




	@Override
	public List<Document> getDocuments() {
		return null;
	}

	@Override
	public Document getDocument(int document_id) {
		return null;
	}

	@Override
	public Document addDocument(Document document) {
		return null;
	}

	@Override
	public Document updateDocument(Document document) {
		return null;
	}

	@Override
	public Document deleteDocument(Document document) {
		return null;
	}

	@Override
	public Long getTotalFileSize() {
		return null;
	}

	@Override
	public boolean createPrivateShareLink(int documentId, String accessURL) {
		return false;
	}

	@Override
	public SharedDocumentLink getLinkedDocumentRefWith(String accessUrl) {
		return null;
	}
}
