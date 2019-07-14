package com.group2.FileShare.document.DeleteObserver;

import com.group2.FileShare.document.Document;

public abstract class Observer
{

    protected DocumentSubject subject;

    public abstract void update(Document document);
}