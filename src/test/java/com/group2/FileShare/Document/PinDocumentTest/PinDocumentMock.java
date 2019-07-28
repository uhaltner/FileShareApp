package com.group2.FileShare.Document.PinDocumentTest;

import com.group2.FileShare.Document.DocumentDAOMock;
import com.group2.FileShare.document.Document;

import java.util.ArrayList;
import java.util.List;

public class PinDocumentMock
{
    private int PIN_DOCUMENTS_LIMIT = 3;
    public static List<Document> documentsListTest;
    DocumentDAOMock documentDAOMock;

    public PinDocumentMock()
    {
        documentDAOMock = new DocumentDAOMock();
        documentsListTest = new ArrayList<Document>();
    }

    public boolean isPinDocumentsLimitAvailable()
    {
        documentsListTest = documentDAOMock.getDocumentList("query", 319,
                false, false);

        int countPinDocuments = 0;

        int documentListSize = documentsListTest.size();

        for (int i=0; i< documentListSize; i++)
        {
            boolean isDocumentPinned = documentsListTest.get(i).isPinned();

            if(isDocumentPinned)
            {
                if(countPinDocuments == PIN_DOCUMENTS_LIMIT)
                {
                    return false;
                }
                countPinDocuments++;
            }
        }

        if (countPinDocuments < PIN_DOCUMENTS_LIMIT)
        {
            return true;
        }
        return false;
    }
}
