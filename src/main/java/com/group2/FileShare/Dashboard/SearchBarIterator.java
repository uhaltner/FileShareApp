package com.group2.FileShare.Dashboard;

import com.group2.FileShare.document.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchBarIterator {

    private List<Document> matchList;

    private Document document;
    private String fileName;
    private String upperCaseFileName;
    private String upperCasePhrase;

    public List<Document> findAll(String phrase, List<Document> list){

        Iterator iter = list.iterator();
        matchList = new ArrayList<>();

        //add each document that contains the phrase to a new list
        while(iter.hasNext()){

            document = (Document) iter.next();
            fileName=  document.getFilename();

            upperCaseFileName = fileName.toUpperCase();
            upperCasePhrase = phrase.toUpperCase();

            if (upperCaseFileName.contains(upperCasePhrase)){
                matchList.add(document);
            }
        }

        return matchList;
    }


}
