package com.group2.FileShare.document.DeleteObserver;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;

public class DateObserver extends Observer
{

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
            e.printStackTrace();
        }

    }
}
