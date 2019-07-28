package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Dashboard.MockObjects.DocumentDAOMock;
import com.group2.FileShare.Dashboard.SortStrategy.*;
import com.group2.FileShare.document.IDocumentDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentSorterStrategyTest {

	IDocumentDAO mockDAO = new DocumentDAOMock();
	DocumentSorter documentSorter = new DocumentSorter(new CreatedSortStrategy());

	String expectedResult = "FileName.txt";

	@Test
	public void ChangeToNullDashboardTest()
	{
		boolean errorFound = false;

		try{
			documentSorter.changeStrategy(null);
		}catch (Exception e){
			errorFound = true;
		}

		assertEquals("Null Sorter Change Test", true, errorFound);
	}

	@Test
	public void InputNullDashboardTest(){
		boolean errorFound = false;

		try{
			DocumentSorter documentSorter = new DocumentSorter(null);
		}catch (Exception e){
			errorFound = true;
		}

		assertEquals("Null Sorter Input Test", true, errorFound);
	}

	@Test
	public void CreatedSortTest()
	{
		ISortStrategy strategy = new CreatedSortStrategy();
		strategy.updateDAO(mockDAO);

		String result = strategy.getSortedDocuments(0,false,false).get(0).getFilename();

		assertEquals("Create Strategy Result Retrieval Test", true, result.equals(expectedResult));
	}

	@Test
	public void FileTypeSortTest()
	{
		ISortStrategy strategy = new FiletypeSortStrategy();
		strategy.updateDAO(mockDAO);

		String result = strategy.getSortedDocuments(0,false,false).get(0).getFilename();

		assertEquals("File Type Strategy Result Retrieval Test", true, result.equals(expectedResult));
	}

	@Test
	public void ModifiedDatetimeSortTest()
	{
		ISortStrategy strategy = new ModifiedSortStrategy();
		strategy.updateDAO(mockDAO);

		String result = strategy.getSortedDocuments(0,false,false).get(0).getFilename();

		assertEquals("Modified Datetime Strategy Result Retrieval Test", true, result.equals(expectedResult));
	}

	@Test
	public void NameSortTest()
	{
		ISortStrategy strategy = new NameSortStrategy();
		strategy.updateDAO(mockDAO);

		String result = strategy.getSortedDocuments(0,false,false).get(0).getFilename();

		assertEquals("Name Strategy Result Retrieval Test", true, result.equals(expectedResult));
	}

	@Test
	public void SizeSortTest()
	{
		ISortStrategy strategy = new SizeSortStrategy();
		strategy.updateDAO(mockDAO);

		String result = strategy.getSortedDocuments(0,false,false).get(0).getFilename();

		assertEquals("File Size Strategy Result Retrieval Test", true, result.equals(expectedResult));
	}

}
