package com.group2.FileShare.Document;

import com.group2.FileShare.document.Document;
import org.junit.Assert;
import org.junit.Test;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentDAOTest
{

    DocumentDAOMock documentDAOMock;
    public static List<Document> documentsListTest = new ArrayList<Document>();

    public DocumentDAOTest ()
    {

        documentDAOMock = new DocumentDAOMock();
    }

    @Test
    public void getDocumentsTest()
    {
        Document document = new Document();
        document.setId(777);
        document.setStorageURL("http://document.gettest.com");
        document.setCreatedDate(new Date());
        document.setSize(327482);
        document.setOwnerId(844);
        document.setFilename("Document.txt");
        document.setDescription("documents get test desc");
        document.setTrashedDate(new Date());
        document.setPublic(false);
        document.setTrashed(false);
        document.setPinned(true);

        documentDAOMock.addDocument(document);

        Document document2 = new Document();
        document2.setId(779);
        document2.setStorageURL("http://document.gettesttwo.com");
        document2.setCreatedDate(new Date());
        document2.setSize(39284);
        document2.setOwnerId(637);
        document2.setFilename("Document2.txt");
        document2.setDescription("documents get test desc");
        document2.setTrashedDate(new Date());
        document2.setPublic(false);
        document2.setTrashed(false);
        document2.setPinned(true);

        documentDAOMock.addDocument(document2);

        Assert.assertTrue(documentDAOMock.getDocuments().size()==2);
    }

    @Test
    public void addDocumentTest()
    {
        Document document3 = new Document();

        document3.setId(346);
        document3.setStorageURL("http://document.addtest.com");
        document3.setCreatedDate(new Date());
        document3.setSize(849774);
        document3.setOwnerId(439);
        document3.setFilename("Document3.txt");
        document3.setDescription("document3 add test desc");
        document3.setTrashedDate(new Date());
        document3.setPublic(false);
        document3.setTrashed(false);
        document3.setPinned(true);

        Assert.assertEquals(documentDAOMock.addDocument(document3).getId(),346);
    }

    @Test
    public void updateDocumentTest()
    {
        Document document4 = new Document();

        document4.setId(646);
        document4.setStorageURL("http://document.updatetest.com");
        document4.setCreatedDate(new Date());
        document4.setSize(884550);
        document4.setOwnerId(342);
        document4.setFilename("Document4.txt");
        document4.setDescription("document desc");
        document4.setTrashedDate(new Date());
        document4.setPublic(false);
        document4.setTrashed(false);
        document4.setPinned(true);

        Document originalDoc = documentDAOMock.addDocument(document4);

        originalDoc.setPinned(false);
        Document updatedDocument = documentDAOMock.updateDocument(originalDoc);

        Assert.assertFalse(updatedDocument.isPinned());
    }

    @Test
    public void deleteDocumentTest()
    {
        Document document5 = new Document();
        document5.setId(837);
        document5.setStorageURL("http://document.deletetest.com");
        document5.setCreatedDate(new Date());
        document5.setSize(781326);
        document5.setOwnerId(184);
        document5.setFilename("Document5.txt");
        document5.setDescription("document5 delete test desc");
        document5.setTrashedDate(new Date());
        document5.setPublic(false);
        document5.setTrashed(true);
        document5.setPinned(true);

        documentDAOMock.addDocument(document5);

        Document document6 = new Document();
        document6.setId(937);
        document6.setStorageURL("http://document.uh.com");
        document6.setCreatedDate(new Date());
        document6.setSize(782364);
        document6.setOwnerId(392);
        document6.setFilename("Document6.txt");
        document6.setDescription("document6 delete test desc");
        document6.setTrashedDate(new Date());
        document6.setPublic(false);
        document6.setTrashed(true);
        document6.setPinned(true);
        documentDAOMock.addDocument(document6);

        Assert.assertNotNull(documentDAOMock.getDocument(937));
        documentsListTest = documentDAOMock.getDocuments();

        for (int i=0; i< documentsListTest.size(); i++)
        {
            int docId = documentsListTest.get(i).getId();
            boolean isDocTrashed = documentsListTest.get(i).isTrashed();

            if(docId == 937 && isDocTrashed)
            {
                documentDAOMock.deleteDocument(documentsListTest.get(i));
            }
        }

        Assert.assertNotEquals(documentDAOMock.getDocument(937).getFilename(), "Document6.txt");

    }

    @Test
    public void createPrivateShareLinkTest()
    {
        Document document7 = new Document();
        document7.setId(739);
        document7.setStorageURL("http://document.privatetest.com");
        document7.setCreatedDate(new Date());
        document7.setSize(82497);
        document7.setOwnerId(193);
        document7.setFilename("Document7.txt");
        document7.setDescription("document7 craete private link test desc");
        document7.setTrashedDate(new Date());
        document7.setPublic(false);
        document7.setTrashed(false);
        document7.setPinned(true);
        Document originalDoc = documentDAOMock.addDocument(document7);

        originalDoc.setPinned(false);
        int DocId = originalDoc.getId();
        String randomAccessString = java.util.UUID.randomUUID().toString();

        boolean isPrivate = documentDAOMock.createPrivateShareLink(DocId, randomAccessString);

        Assert.assertFalse(isPrivate);
    }
}
