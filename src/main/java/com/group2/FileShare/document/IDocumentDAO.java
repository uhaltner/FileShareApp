package com.group2.FileShare.document;

import java.util.List;

public interface IDocumentDAO {
    List<Document> getDocuments();
    Document getDocument(int document_id);
    Document addDocument(Document document);
    Document updateDocument(Document document);
    Document deleteDocument(Document document);
    List<Document> getDocumentList(String query, int userId, boolean publicDocumentsOnly, boolean trashedDocumentsOnly);
    boolean createPrivateShareLink(int documentId, String accessURL);
    SharedLink getLinkedDocumentRefWith(String accessUrl);
}
