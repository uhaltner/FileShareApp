package com.group2.FileShare.document.DeleteObserver;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateObserver extends Observer
{
    private static final Logger logger = LogManager.getLogger(DocumentDAO.class);

    public DateObserver(DocumentSubject subject)
    {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update(Document document)
    {
        try
        {
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.deleteDocument(document);
        }
        catch (Exception e)
        {
            logger.log(Level.ERROR, "Failed to delete document at update(): ", e);
        }

    }
}
