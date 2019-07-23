package com.group2.FileShare.document.PinDocument;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PinDocumentsLimit
{
    private DocumentDAO documentDAO;
    List<Document> documentList;
    private static final int PIN_DOCUMENTS_LIMIT = 3;
    private static final Logger logger = LogManager.getLogger(PinDocumentsLimit.class);

    public PinDocumentsLimit()
    {
        documentDAO = new DocumentDAO();
    }

    public boolean isPinDocumentsLimitAvailable()
    {
       documentList = documentDAO.getDocuments();
       int countPinDocuments = 0;

        try
        {
            int documentListSize = documentList.size();

            for (int i=0; i< documentListSize; i++)
            {
                boolean isDocumentPinned = documentList.get(i).isPinned();

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
        }
        catch (Exception e)
        {
            logger.log(Level.WARN, "Failed to verify pin document limit at isPinDocumentsLimitAvailable(): ", e);
        }
        return false;
    }
}
