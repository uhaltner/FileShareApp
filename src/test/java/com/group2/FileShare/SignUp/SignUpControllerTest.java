package com.group2.FileShare.SignUp;

import com.group2.FileShare.ProfileManagement.IPasswordValidator;
import com.group2.FileShare.ProfileManagement.MockObjects.SignUpDAOMock;
import com.group2.FileShare.SignUp.MockObjects.PasswordValidatorMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignUpControllerTest {

	private SignUpController signUpController = new SignUpController();

	private SignUpForm signUpForm = new SignUpForm();
	private ISignUpDAO signUpDAO = new SignUpDAOMock();
	private IPasswordValidator passwordValidator = new PasswordValidatorMock();
	private HttpSession session = null;

	@Test
	public void InvalidUserTest()
	{
		SignUpController.Feedback feedback;

		signUpForm.setEmail("existing@email.com");
		signUpForm.setPassword("validPassword1");

		feedback = signUpController.signUpUser(signUpForm, signUpDAO, passwordValidator, session);

		assertEquals("Invalid Email Test",SignUpController.Feedback.INVALID_USER ,feedback);
	}

	@Test
	public void InvalidPasswordTest()
	{
		SignUpController.Feedback feedback;

		signUpForm.setEmail("valid@email.com");
		signUpForm.setPassword("invalidPassword1");

		feedback = signUpController.signUpUser(signUpForm, signUpDAO, passwordValidator, session);

		assertEquals("invalid Password Test",SignUpController.Feedback.INVALID_PASSWORD ,feedback);
	}

	@Test
	public void ErrorHandlingTest()
	{
		SignUpController.Feedback feedback;
		feedback = signUpController.signUpUser(null, null, null, session);

		assertEquals("Input Error Test",SignUpController.Feedback.ERROR ,feedback);
	}

	@Test
	public void validSignUpTest()
	{
		SignUpController.Feedback feedback;

		signUpForm.setEmail("new@email.com");
		signUpForm.setPassword("validPassword1");

		feedback = signUpController.signUpUser(signUpForm, signUpDAO, passwordValidator, session);

		assertEquals("Valid SignUp Test",SignUpController.Feedback.OK ,feedback);
	}





}
