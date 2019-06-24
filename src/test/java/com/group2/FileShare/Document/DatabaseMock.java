package com.group2.FileShare.Document;

import com.group2.FileShare.document.Document;

public class DatabaseMock {




    public Document addDocumentsToMockDb(Document document)
    {
        Document doc = new Document();
        doc.setPinned(document.isPinned());
        doc.setPublic(document.isPublic());
        doc.setTrashed(document.isTrashed());
        doc.setTrashedDate(document.getTrashedDate());
        doc.setDescription(document.getDescription());
        doc.setFilename(document.getFilename());
        doc.setOwnerId(document.getOwnerId());
        doc.setSize(document.getSize());

        doc.setStorageURL(document.getStorageURL());
        doc.setCreatedDate(document.getCreatedDate());
        doc.setId(document.getId());

        return doc;

    }


}
