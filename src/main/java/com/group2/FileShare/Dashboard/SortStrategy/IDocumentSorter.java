package com.group2.FileShare.Dashboard.SortStrategy;

import com.group2.FileShare.document.Document;

import java.util.List;

public interface IDocumentSorter {

    public void changeStrategy(ISortStrategy strategy);
    public List<Document> executeStrategy(int userId, boolean onlyPublicDocuments, boolean onlyTrashedDocuments);
}
