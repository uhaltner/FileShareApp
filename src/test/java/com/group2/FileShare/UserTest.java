package com.group2.FileShare;

import static org.junit.Assert.*;

import com.group2.FileShare.User.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

	@Test
	public void returnCorrectFirstName() {
		
		User u = new User("John","Smith");
		
		assertEquals("getFirstNameTest", "John", u.getFirstName());
	}
	
	@Test
	public void returnCorrectLastName() {
		
		User u = new User("John","Smith");
		
		assertEquals("getLastNameTest", "Smith", u.getLastName());
		
	}

}
