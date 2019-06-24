package com.group2.FileShare.Compression;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.group2.FileShare.Compression.ZipCompression;

public class ZipCompressionTest {

	@Test
	public void test() {
        MockMultipartFile mockFile = new MockMultipartFile("data", "README.txt", "text/plain", "README".getBytes());
        ZipCompression compression = new ZipCompression();
        File result = compression.compressFile(mockFile);
       
        String name = result.getName();
        assertTrue(result.exists() && name.substring(name.lastIndexOf(".")).equals(".zip"));
        result.delete();
	}

}
