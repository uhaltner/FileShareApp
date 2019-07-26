package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.IMailService;
import com.group2.FileShare.MailService;
import com.group2.FileShare.ProfileManagement.MockObjects.MailServiceMock;
import com.group2.FileShare.ProfileManagement.MockObjects.PasswordDAOMock;
import com.group2.FileShare.ProfileManagement.MockObjects.SignUpDAOMock;
import com.group2.FileShare.ProfileManagement.PasswordRecovery.PasswordRecoveryController;
import com.group2.FileShare.SignUp.ISignUpDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordRecoveryControllerTest {


	private PasswordRecoveryController passwordRecoveryController = new PasswordRecoveryController();

	private IMailService mailService = new MailServiceMock();
	private IPasswordDAO passwordDAO = new PasswordDAOMock();
	private ISignUpDAO signUpDAO = new SignUpDAOMock();

	@Test
	public void validUserTest(){

		String email = "valid@email.com";
		PasswordRecoveryController.Feedback feedback;

		feedback = passwordRecoveryController.passwordRecovery(signUpDAO,passwordDAO,mailService,email);

		assertEquals("Valid User Test", PasswordRecoveryController.Feedback.OK, feedback);
	}

	@Test
	public void invalidUserTest(){

		String email = "notValid@email.com";
		PasswordRecoveryController.Feedback feedback;

		feedback = passwordRecoveryController.passwordRecovery(signUpDAO,passwordDAO,mailService,email);

		assertEquals("Invalid User Test", PasswordRecoveryController.Feedback.INVALID_USER, feedback);
	}

	@Test
	public void errorHandlingTest(){

		String email = "valid@email.com";
		PasswordRecoveryController.Feedback feedback;

		feedback = passwordRecoveryController.passwordRecovery(null, null, null, email);

		assertEquals("Error Handling Test", PasswordRecoveryController.Feedback.ERROR, feedback);
	}




}
