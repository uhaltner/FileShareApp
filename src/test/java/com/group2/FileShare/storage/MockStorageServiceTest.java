package com.group2.FileShare.storage;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MockStorageServiceTest {
	private IStorage storageService;

	@Before
	public void setUp() throws Exception {
		storageService = MockStorageService.getInstance();
		storageService.uploadFile(new File("mockfile1"), "mockFile1.txt");
		storageService.uploadFile(new File("mockfile2"), "mockFile2.txt");
		storageService.uploadFile(new File("mockfile3"), "mockFile3.txt");
	}

	@Test
	public void getInstanceTest() {
		Assert.assertNotNull(MockStorageService.getInstance());
	}
	
	@Test
	public void fileUploadTest() {
		File uploadFile = new File("mockfile4");
		storageService.uploadFile(uploadFile, "mockFile4.txt");
		Assert.assertNotNull(storageService.downloadFile("mockFile4.txt"));
		Assert.assertEquals(storageService.downloadFile("mockFile4.txt"), "https://fileshare-special-storage.com/mockFile4.txt");
	}
	
	@Test
	public void fileDownloadTest() {
		String downloadLink = storageService.downloadFile("mockFile1.txt");
		Assert.assertNotNull(downloadLink);
		Assert.assertEquals(downloadLink, "https://fileshare-special-storage.com/mockFile1.txt");
		downloadLink = storageService.downloadFile("mockFile6.txt");
		Assert.assertNull(downloadLink);
	}
	
	@Test
	public void fileDeleteTest() {
		boolean deletedFile = storageService.deleteFile("mockFile2.txt");
		Assert.assertEquals(deletedFile,true);
		deletedFile = storageService.deleteFile("mockFile5.txt");
		Assert.assertEquals(deletedFile,false);
		deletedFile = storageService.deleteFile("mockFile2.txt");
		Assert.assertEquals(deletedFile,false);
	}

}
