package com.group2.FileShare.Document.DocumentExpriyTest;

import com.group2.FileShare.Document.DocumentDAOMock;
import com.group2.FileShare.document.Document;

import javax.print.Doc;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeleteDocumentMock
{
    private static long DELETE_DOCUMENT_EXPIRY_PERIOD = 30;
    public DocumentDAOMock documentDAOMock;
    private Date currentDate;
    private Date trashedDate;
    String pattern = "yyyy-MM-dd";

    public DeleteDocumentMock()
    {
        documentDAOMock = new DocumentDAOMock();
    }

    public List<Document> deleteDocumentPermanently() throws IndexOutOfBoundsException
    {
        List<Document> documentsListTest;
        List<Document> expiryDocumentList = new ArrayList<>();
        documentsListTest = documentDAOMock.getDocumentList("query", 729, false, true);

        try
        {
            int documentListSize = documentsListTest.size();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            // Tests cannot be performed with current time
            currentDate = simpleDateFormat.parse("2019-07-30");

            for (int i=0; i< documentListSize; i++)
            {
                boolean isDocumentTrashed = documentsListTest.get(i).isTrashed();

                if(isDocumentTrashed)
                {
                    trashedDate = documentsListTest.get(i).getTrashedDate();

                    long timeDifference = currentDate.getTime() - trashedDate.getTime();

                    // computing in milliseconds
                    if(timeDifference > DELETE_DOCUMENT_EXPIRY_PERIOD*24*60*60*1000)
                    {
                        expiryDocumentList.add(documentsListTest.get(i));
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        documentsListTest.removeAll(expiryDocumentList);
        return documentsListTest;
    }
}
