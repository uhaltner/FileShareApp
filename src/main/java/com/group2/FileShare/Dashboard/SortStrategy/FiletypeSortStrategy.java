package com.group2.FileShare.Dashboard.SortStrategy;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;

import java.util.List;

public class FiletypeSortStrategy implements ISortStrategy {

    private static DocumentDAO documentDAO = new DocumentDAO();

    @Override
    public List<Document> getSortedDocuments(int userId, boolean publicDocumentsOnly, boolean onlyTrashedDocuments) {

        String query = "{ call get_documents_filetype_filter(?,?,?) }";

        return documentDAO.getDocumentList(query, userId, publicDocumentsOnly, onlyTrashedDocuments);
    }
}
