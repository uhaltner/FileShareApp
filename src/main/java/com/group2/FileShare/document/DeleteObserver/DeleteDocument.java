package com.group2.FileShare.document.DeleteObserver;

import com.group2.FileShare.DefaultProperties;
import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DeleteDocument
{
    private DocumentSubject documentSubject;
    private long DELETE_DOCUMENT_EXPIRY_PERIOD;
    private Timestamp currentTimestamp;
    private Date trashedDate;
    private static final Logger logger = LogManager.getLogger(DeleteDocument.class);

    public void deleteDocumentPermanently()
    {
        List<Document> documentList;

        try
        {
            DELETE_DOCUMENT_EXPIRY_PERIOD = DefaultProperties.getInstance().getDeleteDocumentExpiry();
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

                    // computing in milliseconds
                    if(timeDifference > DELETE_DOCUMENT_EXPIRY_PERIOD*24*60*60*1000)
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
