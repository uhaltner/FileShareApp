package com.group2.FileShare.Document;

import static org.junit.Assert.*;

import com.group2.FileShare.document.SharedDocumentLink;
import com.group2.FileShare.document.SharedDocumentLinkValidator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.Validator;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SharedDocumentLinkValidatorTest
{
	private static SharedDocumentLinkValidator sharedDocumentLinkValidator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		sharedDocumentLinkValidator = new SharedDocumentLinkValidator();
		sharedDocumentLinkValidator.isJUNITTest = true;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		sharedDocumentLinkValidator = null;
	}

	@Test
	public void validSharedDoumentLinkSuccessTest()
	{
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = df.format(date);

		SharedDocumentLink sharedDocumentLink = new SharedDocumentLink(1243, 434, formattedDate);
		String validationErrorResponse = sharedDocumentLinkValidator.validateDocumentLinkWithErrorResponse(sharedDocumentLink);
		System.out.println(validationErrorResponse);
		assertTrue(validationErrorResponse.isEmpty());
	}

	@Test
	public void expiredSharedDoumentLinkFailTest()
	{
		SharedDocumentLink sharedDocumentLink = new SharedDocumentLink(1243, 434, "2016-08-15 07:06:37");
		String validationErrorResponse = sharedDocumentLinkValidator.validateDocumentLinkWithErrorResponse(sharedDocumentLink);
		assertTrue(validationErrorResponse.equals("Sorry, the link has expired"));
	}

	@Test
	public void invalidSharedDoumentLinkExpirationFailTest()
	{
		SharedDocumentLink sharedDocumentLink = new SharedDocumentLink(1243, 434, "-08-15 te:06:37");
		String validationErrorResponse = sharedDocumentLinkValidator.validateDocumentLinkWithErrorResponse(sharedDocumentLink);
		assertTrue(validationErrorResponse.equals("Can't access the file at the moment, please try again later"));
	}

	@Test
	public void nullSharedDoumentLinkExpirationFailTest()
	{
		String validationErrorResponse = sharedDocumentLinkValidator.validateDocumentLinkWithErrorResponse(null);
		assertTrue(validationErrorResponse.equals("This link doesn't exist in Fileshare App"));
	}

}
