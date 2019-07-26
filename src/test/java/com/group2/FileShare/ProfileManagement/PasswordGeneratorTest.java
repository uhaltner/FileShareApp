package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRecovery.PasswordGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordGeneratorTest {

	PasswordGenerator generator = new PasswordGenerator();

	@Test
	public void emptyResultTest(){
		assertEquals("Empty Generated Password Test", false , generator.generate().isEmpty());
	}

	@Test
	public void notNullTest(){
		assertEquals("Null Generated Password Test", false ,generator.generate() == null);
	}
}
