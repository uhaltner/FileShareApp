package com.group2.FileShare.document;

import java.util.List;

public interface IDocumentDAO {
    List<Document> getDocuments();
    Document getDocument(int document_id);
    Document addDocument(Document document);
    Document updateDocument(Document document);
    Document deleteDocument(Document document);
    boolean createPrivateShareLink(int documentId, String accessURL, String linkedFileDescription);
}
