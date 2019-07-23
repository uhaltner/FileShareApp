package com.group2.FileShare.Document;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.IDocumentDAO;

import java.util.ArrayList;
import java.util.List;

public class DocumentDAOMock implements IDocumentDAO {

    DatabaseMock databaseMock;
    public static List<Document> documents = new ArrayList<Document>();

    DocumentDAOMock()
    {
        databaseMock  = new DatabaseMock();
    }

    public List<Document> getDocuments()
    {
        return documents;
    }

    @Override
    public Document getDocument(int document_id)
    {
        Document document = new Document();

        for (int i=0; i< documents.size(); i++)
        {
            int docId = documents.get(i).getId();

            if(docId == document_id)
            {
                document = documents.get(i);
            }
        }

        return document;
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
        Document updatedDocument = document;
        return updatedDocument;
    }

    public Document deleteDocument(Document document)
    {
        documents.remove(document);
        return null;
    }
    
    public boolean createPrivateShareLink(int documentId, String accessURL)
    {
        if (null == accessURL && documentId != 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public Long getTotalFileSize() 
    {
      return null;
    }
  
}
