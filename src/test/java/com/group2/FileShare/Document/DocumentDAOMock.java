package com.group2.FileShare.Document;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.IDocumentDAO;
import com.group2.FileShare.document.SharedLink;

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

    @Override
    public Long getTotalFileSize() {
        return null;
    }

    @Override
    public List<Document> getDocumentList(String query, int userId, boolean publicDocumentsOnly, boolean trashedDocumentsOnly) {

        Document document = new Document();
        List<Document> newDocumentList = new ArrayList<Document>();

        for (int i=0; i< documents.size(); i++)
        {
            int ownerID = documents.get(i).getOwnerId();
            boolean isPublicDoc = documents.get(i).isPublic();
            boolean isTrashedDoc = documents.get(i).isTrashed();

            if(ownerID == userId && !isPublicDoc && !isTrashedDoc)
            {
                document = documents.get(i);
                newDocumentList.add(document);
            }
        }

        return newDocumentList;
    }

    public boolean createPrivateShareLink(int documentId, String accessURL)
    {
        if (null != accessURL && documentId != 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public SharedLink getLinkedDocumentRefWith(String accessUrl)
    {
        if (null != accessUrl)
        {
            SharedLink sharedDocumentRefernceTest = new SharedLink(328947243,
                    841, "2019-08-15 07:06:37");
            return sharedDocumentRefernceTest;
        }
        return null;
    }

}
