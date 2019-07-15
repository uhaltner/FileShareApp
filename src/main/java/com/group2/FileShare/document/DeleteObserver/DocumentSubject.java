package com.group2.FileShare.document.DeleteObserver;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DocumentSubject
{

    private List<Observer> observers = new ArrayList<Observer>();
    private static final Logger logger = LogManager.getLogger(DocumentDAO.class);

    public void attach(Observer observer)
    {
        observers.add(observer);
    }

    public void notifyObservers(Document document)
    {
        try
        {
            for (Observer observer : observers)
            {
                observer.update(document);
            }
        }
        catch (Exception e)
        {
            logger.log(Level.ERROR, "Failed to notify observers at notifyObservers(): ", e);
        }

    }
}
