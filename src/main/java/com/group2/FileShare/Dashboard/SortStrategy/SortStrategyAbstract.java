package com.group2.FileShare.Dashboard.SortStrategy;

import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;
import com.group2.FileShare.document.IDocumentDAO;

import java.util.List;

public abstract class SortStrategyAbstract implements ISortStrategy{

	protected IDocumentDAO documentDAO;

	public SortStrategyAbstract(){
		this.documentDAO = new DocumentDAO();
	}

	public void updateDAO(IDocumentDAO documentDAO){
		this.documentDAO = documentDAO;
	}

	@Override
	public abstract List<Document> getSortedDocuments(int userId, boolean onlyPublicDocuments, boolean onlyTrashedDocuments);
}
