package com.group2.FileShare.Dashboard.SortStrategy;

import com.group2.FileShare.document.Document;

import java.util.List;

public class CreatedSortStrategy extends SortStrategyAbstract {

    public CreatedSortStrategy(){
        super();
    }

    @Override
    public List<Document> getSortedDocuments(int userId, boolean publicDocumentsOnly, boolean onlyTrashedDocuments) {

        String query = "{ call get_documents_created_filter(?,?,?) }";

        return documentDAO.getDocumentList(query, userId, publicDocumentsOnly, onlyTrashedDocuments);
    }
}
