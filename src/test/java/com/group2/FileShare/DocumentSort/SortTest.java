package com.group2.FileShare.DocumentSort;

import com.group2.FileShare.Dashboard.DocumentSorter;
import com.group2.FileShare.Dashboard.SortStrategy.*;
import com.group2.FileShare.document.Document;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SortTest {


    @Test
    public void NameSort_NotEmptyTest(){

        int userId = 1;
        boolean isPublic = false;
        List<Document> documentList = new ArrayList<>();

        DocumentSorter sorter = new DocumentSorter(new NameSortStrategy());
        documentList = sorter.executeStrategy(userId, isPublic);

        assertEquals("Sorted by name document list test", false, documentList.isEmpty());
    }

    @Test
    public void SizeSort_NotEmptyTest(){

        int userId = 1;
        boolean isPublic = false;
        List<Document> documentList = new ArrayList<>();

        DocumentSorter sorter = new DocumentSorter(new SizeSortStrategy());
        documentList = sorter.executeStrategy(userId, isPublic);

        assertEquals("Sorted by size document list test", false, documentList.isEmpty());
    }

    @Test
    public void FileTypeSort_NotEmptyTest(){

        int userId = 1;
        boolean isPublic = false;
        List<Document> documentList = new ArrayList<>();

        DocumentSorter sorter = new DocumentSorter(new FiletypeSortStrategy());
        documentList = sorter.executeStrategy(userId, isPublic);

        assertEquals("Sorted by file type document list test", false, documentList.isEmpty());
    }

    @Test
    public void CreatedDatetimeSort_NotEmptyTest(){

        int userId = 1;
        boolean isPublic = false;
        List<Document> documentList = new ArrayList<>();

        DocumentSorter sorter = new DocumentSorter(new CreatedSortStrategy());
        documentList = sorter.executeStrategy(userId, isPublic);

        assertEquals("Sorted by created datetime document list test", false, documentList.isEmpty());
    }

    @Test
    public void ModifiedSort_NotEmptyTest(){

        int userId = 1;
        boolean isPublic = false;
        List<Document> documentList = new ArrayList<>();

        DocumentSorter sorter = new DocumentSorter(new ModifiedSortStrategy());
        documentList = sorter.executeStrategy(userId, isPublic);

        assertEquals("Sorted by modified datetime document list test", false, documentList.isEmpty());
    }
}
