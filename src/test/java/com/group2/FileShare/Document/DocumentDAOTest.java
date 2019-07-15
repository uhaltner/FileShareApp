package com.group2.FileShare.Document;

import com.group2.FileShare.document.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class DocumentDAOTest {

    DocumentDAOMock documentDAOMock;

    public DocumentDAOTest ()
    {
        documentDAOMock = new DocumentDAOMock();
    }
    @Test
    public void getDocumentsTest()
    {
        Assert.assertNotNull(documentDAOMock.getDocuments());
    }

    @Test
    public void addDocumentTest()
    {
        Document document = new Document();
        document.setId(777);
        document.setStorageURL("http://document.uh.com");
        document.setCreatedDate(new Date());
        document.setSize(782364);
        document.setOwnerId(392);
        document.setFilename("Document2.txt");
        document.setDescription("document desc");
        document.setTrashedDate(new Date());
        document.setPublic(false);
        document.setTrashed(false);
        document.setPinned(true);

        Assert.assertEquals(documentDAOMock.addDocument(document).getId(),777);
    }


}
