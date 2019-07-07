package com.group2.FileShare.Dashboard.SortStrategy;

import com.group2.FileShare.document.Document;

import java.util.List;

public interface ISortStrategy {

    public List<Document> getSortedDocuments(int userId, boolean publicDocumentsOnly);
}
