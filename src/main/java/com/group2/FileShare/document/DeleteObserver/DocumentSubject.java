package com.group2.FileShare.document.DeleteObserver;

import com.group2.FileShare.document.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentSubject
{

    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer)
    {
        observers.add(observer);
    }

    public void notifyObservers(Document document)
    {
        try{
            for (Observer observer : observers)
            {
                observer.update(document);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
