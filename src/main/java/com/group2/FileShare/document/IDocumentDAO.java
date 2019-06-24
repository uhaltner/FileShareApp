package com.group2.FileShare.document;

import java.util.List;

public interface IDocumentDAO {
    List<Document> getDocuments();
    Document addDocument(Document document);
    Document updateDocument(Document document);
    Document deleteDocument(Document document);
}
