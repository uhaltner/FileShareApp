package com.group2.FileShare.Dashboard.SortStrategy;

import com.group2.FileShare.document.Document;

import java.util.List;

public class ModifiedSortStrategy extends SortStrategyAbstract{

    public ModifiedSortStrategy(){
        super();
    }

    @Override
    public List<Document> getSortedDocuments(int userId, boolean publicDocumentsOnly, boolean onlyTrashedDocuments) {

        String query = "{ call get_documents_modified_filter(?,?,?) }";

        return documentDAO.getDocumentList(query, userId, publicDocumentsOnly, onlyTrashedDocuments);
    }
}
