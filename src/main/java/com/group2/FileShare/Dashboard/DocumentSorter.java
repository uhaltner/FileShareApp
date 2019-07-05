package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Dashboard.SortStrategy.ISortStrategy;
import com.group2.FileShare.document.Document;

import java.util.List;


public class DocumentSorter {

    private ISortStrategy strategy;

    public DocumentSorter(ISortStrategy strategy)
    {
        this.strategy = strategy;
    }

    public void changeStrategy(ISortStrategy strategy)
    {
        this.strategy = strategy;
    }

    public List<Document> executeStrategy(int userId, boolean publicDocumentsOnly)
    {
        return strategy.getSortedDocuments(userId, publicDocumentsOnly);
    }



}
