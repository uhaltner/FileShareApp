package com.group2.FileShare.Dashboard.SortStrategy;

import com.group2.FileShare.document.Document;

import java.util.List;


public class DocumentSorter implements IDocumentSorter{

    private ISortStrategy strategy;

    public DocumentSorter(ISortStrategy strategy)
    {
        this.strategy = strategy;
    }

    @Override
    public void changeStrategy(ISortStrategy strategy)
    {
        this.strategy = strategy;
    }

    @Override
    public List<Document> executeStrategy(int userId, boolean onlyPublicDocuments, boolean onlyTrashedDocuments)
    {
        return strategy.getSortedDocuments(userId, onlyPublicDocuments, onlyTrashedDocuments);
    }



}
