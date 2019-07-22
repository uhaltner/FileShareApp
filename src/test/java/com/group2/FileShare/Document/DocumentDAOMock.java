package com.group2.FileShare.Document;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.IDocumentDAO;

import java.util.ArrayList;
import java.util.List;

public class DocumentDAOMock implements IDocumentDAO {

    DatabaseMock databaseMock;
    public static List<Document> documents;

    DocumentDAOMock()
    {
        databaseMock  = new DatabaseMock();
        documents = new ArrayList<Document>();
    }

    public List<Document> getDocuments()
    {
        return documents;
    }


    public Document addDocument(Document document)
    {
        Document doc;

        doc = databaseMock.addDocumentsToMockDb(document);
        documents.add(doc);
        return doc;

    }


    public Document updateDocument(Document document)
    {
        return null;
    }

    public Document deleteDocument(Document document)
    {
        return null;
    }
    
    public boolean createPrivateShareLink(int documentId, String accessURL)
    {
    	return false;
    }

	@Override
	public Document getDocument(int document_id) {
		return null;
	}

	@Override
	public Long getTotalFileSize() {
		return null;
	}
}
