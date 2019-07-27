package com.group2.FileShare.Document.PinDocumentTest;

import com.group2.FileShare.Document.DocumentDAOMock;
import com.group2.FileShare.document.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class PinDocumentsLimitTest
{
    DocumentDAOMock documentDAOMock;
    PinDocumentMock pinDocumentMock;
    private boolean isPinDocumentValid;

    public PinDocumentsLimitTest()
    {
        documentDAOMock = new DocumentDAOMock();
        pinDocumentMock = new PinDocumentMock();
    }

    @Test
    public void verifyPinDocumentsLimitTest()
    {
        Document document = new Document();
        document.setId(138);
        document.setStorageURL("http://document.gettest.com");
        document.setCreatedDate(new Date());
        document.setSize(182793);
        document.setOwnerId(319);
        document.setFilename("Document.txt");
        document.setDescription("verify Pin Documents Limit Test for document");
        document.setTrashedDate(new Date());
        document.setPublic(false);
        document.setTrashed(false);
        document.setPinned(true);

        documentDAOMock.addDocument(document);

        Document document2 = new Document();
        document2.setId(128);
        document2.setStorageURL("http://document.gettesttwo.com");
        document2.setCreatedDate(new Date());
        document2.setSize(81032);
        document2.setOwnerId(319);
        document2.setFilename("Document2.txt");
        document2.setDescription("verify Pin Documents Limit Test for document2");
        document2.setTrashedDate(new Date());
        document2.setPublic(false);
        document2.setTrashed(false);
        document2.setPinned(false);

        documentDAOMock.addDocument(document2);

        Document document3 = new Document();
        document3.setId(182);
        document3.setStorageURL("http://document.gettesttwo.com");
        document3.setCreatedDate(new Date());
        document3.setSize(901823);
        document3.setOwnerId(319);
        document3.setFilename("Document3.txt");
        document3.setDescription("verify Pin Documents Limit Test for document3");
        document3.setTrashedDate(new Date());
        document3.setPublic(false);
        document3.setTrashed(false);
        document3.setPinned(false);

        documentDAOMock.addDocument(document3);

        isPinDocumentValid = pinDocumentMock.isPinDocumentsLimitAvailable();

        Assert.assertTrue(isPinDocumentValid);

        Document document4 = new Document();
        document4.setId(371);
        document4.setStorageURL("http://document.gettesttwo.com");
        document4.setCreatedDate(new Date());
        document4.setSize(318123);
        document4.setOwnerId(319);
        document4.setFilename("Document4.txt");
        document4.setDescription("verify Pin Documents Limit Test for document4");
        document4.setTrashedDate(new Date());
        document4.setPublic(false);
        document4.setTrashed(false);
        document4.setPinned(true);

        documentDAOMock.addDocument(document4);

        isPinDocumentValid = pinDocumentMock.isPinDocumentsLimitAvailable();

        Assert.assertTrue(isPinDocumentValid);

        Document document5 = new Document();
        document5.setId(109);
        document5.setStorageURL("http://document.gettesttwo.com");
        document5.setCreatedDate(new Date());
        document5.setSize(120388);
        document5.setOwnerId(319);
        document5.setFilename("Document5.txt");
        document5.setDescription("verify Pin Documents Limit Test for document5");
        document5.setTrashedDate(new Date());
        document5.setPublic(false);
        document5.setTrashed(false);
        document5.setPinned(true);

        documentDAOMock.addDocument(document5);

        isPinDocumentValid = pinDocumentMock.isPinDocumentsLimitAvailable();

        Assert.assertFalse(isPinDocumentValid);

    }
}
