package com.group2.FileShare.Dashboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchBarHandlerTest {

	@Test
	public void ResetTest()
	{

		SearchBarHandler searchBarHandler = new SearchBarHandler();
		searchBarHandler.setSearchPhrase("Word");
		searchBarHandler.setSearchRequired(true);

		searchBarHandler.reset();

		boolean result = false;

		if(searchBarHandler.isSearchRequired()== false && searchBarHandler.getSearchPhrase().isEmpty() ){
			result = true;
		}else {
			result = false;
		}

		assertEquals("Proper Reset Successful", true, result);
	}
}
