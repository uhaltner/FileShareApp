package com.group2.FileShare.Document.DocumentExpriyTest;

import com.group2.FileShare.Document.DocumentDAOMock;
import com.group2.FileShare.document.Document;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeleteDocumentTest
{
    DocumentDAOMock documentDAOMock;
    DeleteDocumentMock deleteDocumentMock;

    public DeleteDocumentTest()
    {
        documentDAOMock = new DocumentDAOMock();
        deleteDocumentMock = new DeleteDocumentMock();
    }

    @Test
    public void expiryDocumentDeleteTest() throws ParseException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date createdDateDocument1 = simpleDateFormat.parse("2019-04-11");
        Date trashedDateDocument1 = simpleDateFormat.parse("2019-07-19");

        Document document = new Document();
        document.setId(378);
        document.setStorageURL("http://document.documentexpirytest.com");
        document.setCreatedDate(createdDateDocument1);
        document.setSize(910238);
        document.setOwnerId(729);
        document.setFilename("Document.txt");
        document.setDescription("verify Pin Documents Limit Test for document");
        document.setTrashedDate(trashedDateDocument1);
        document.setPublic(false);
        document.setTrashed(true);
        document.setPinned(true);

        documentDAOMock.addDocument(document);

        Date createdDateDocument2 = simpleDateFormat.parse("2019-02-14");
        Date trashedDateDocument2 = simpleDateFormat.parse("2019-06-29");

        Document document2 = new Document();
        document2.setId(128);
        document2.setStorageURL("http://document.documentexpirytesttwo.com");
        document2.setCreatedDate(createdDateDocument2);
        document2.setSize(81032);
        document2.setOwnerId(729);
        document2.setFilename("Document2.txt");
        document2.setDescription("verify Pin Documents Limit Test for document2");
        document2.setTrashedDate(trashedDateDocument2);
        document2.setPublic(false);
        document2.setTrashed(true);
        document2.setPinned(false);

        documentDAOMock.addDocument(document2);

        Date createdDateDocument3 = simpleDateFormat.parse("2019-01-29");
        Date trashedDateDocument3 = simpleDateFormat.parse("2019-07-15");

        Document document3 = new Document();
        document3.setId(182);
        document3.setStorageURL("http://document.documentexpirytestthree.com");
        document3.setCreatedDate(createdDateDocument3);
        document3.setSize(901823);
        document3.setOwnerId(729);
        document3.setFilename("Document3.txt");
        document3.setDescription("verify Pin Documents Limit Test for document3");
        document3.setTrashedDate(trashedDateDocument3);
        document3.setPublic(false);
        document3.setTrashed(true);
        document3.setPinned(false);

        documentDAOMock.addDocument(document3);

        Date createdDateDocument4 = simpleDateFormat.parse("2019-04-15");
        Date trashedDateDocument4 = simpleDateFormat.parse("2019-07-28");

        Document document4 = new Document();
        document4.setId(371);
        document4.setStorageURL("http://document.documentexpirytestfour.com");
        document4.setCreatedDate(createdDateDocument4);
        document4.setSize(318123);
        document4.setOwnerId(729);
        document4.setFilename("Document4.txt");
        document4.setDescription("verify Pin Documents Limit Test for document4");
        document4.setTrashedDate(trashedDateDocument4);
        document4.setPublic(false);
        document4.setTrashed(true);
        document4.setPinned(true);

        documentDAOMock.addDocument(document4);

        List<Document> documentsListTest = deleteDocumentMock.deleteDocumentPermanently();

        Assert.assertEquals(documentsListTest.size(),3);

    }
}