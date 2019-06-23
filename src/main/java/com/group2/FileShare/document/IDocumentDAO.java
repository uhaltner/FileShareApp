package com.group2.FileShare.document;

import java.util.List;

public interface IDocumentDAO {
    public DocumentDAO getDocumentDAOInstance();
    List<Document> getDocuments(); //all documents for current user
    Document addDocument(Document document); //returns document with ID from the db
    Document updateDocument(Document document);
    Document deleteDocument(Document document);
}
