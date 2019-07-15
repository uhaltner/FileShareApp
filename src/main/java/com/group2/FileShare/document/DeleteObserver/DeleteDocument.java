package com.group2.FileShare.document.DeleteObserver;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentController;
import com.group2.FileShare.document.DocumentDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DeleteDocument
{
    private static DocumentSubject documentSubject;
    private static final int NUMBER_OF_DAYS = 30;
    private static Timestamp currentTimestamp;
    private static Date trashedDate;
    private static final Logger logger = LogManager.getLogger(DocumentDAO.class);

    public static void deleteDocumentPermanently()
    {
        List<Document> documentList;

        try
        {
            documentList = DocumentController.getDocumentList();
            int documentListSize = documentList.size();
            documentSubject = new DocumentSubject();
            new DateObserver(documentSubject);
            currentTimestamp = new Timestamp(System.currentTimeMillis());

            for (int i=0; i< documentListSize; i++)
            {
                boolean isDocumentTrashed = documentList.get(i).isTrashed();

                if(isDocumentTrashed)
                {
                    trashedDate = documentList.get(i).getTrashedDate();
                    long timeDifference = currentTimestamp.getTime() - trashedDate.getTime();

                    if(timeDifference > NUMBER_OF_DAYS*24*60*60*1000)
                    {
                        documentSubject.notifyObservers(documentList.get(i));
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.log(Level.WARN, "Failed to delete document permanently at deleteDocumentPermanently(): ", e);
        }
    }
}
