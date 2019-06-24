package com.group2.FileShare.SignIn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignInValidatorTest {

    private static Validator signInValidator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		signInValidator = new SignInValidator();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
        signInValidator = null;
        assertNull(signInValidator);
	}
    
    @Test 
    public void validationSuccessTest() {
    	SignInForm signInForm = new SignInForm();
    	signInForm.setEmail("babatunde.adeniyi@dal.ca");
    	signInForm.setPassword("theP@$w0rd123");
        BindException errors = new BindException(signInForm, "signInForm");
        signInValidator.validate(signInForm, errors);
        assertFalse(errors.hasErrors());
    }
    
    @Test 
    public void validationEmailFailureTest() {
    	SignInForm signInForm = new SignInForm();
    	signInForm.setEmail("babatunde.adeniyi");
    	signInForm.setPassword("theP@$w0rd123");
        BindException errors = new BindException(signInForm, "signInForm");
        System.out.println(signInValidator);
        signInValidator.validate(signInForm, errors);
        assertTrue(errors.hasErrors());
    }
    
    @Test 
    public void validationPasswordFailureTest() {
    	SignInForm signInForm = new SignInForm();
    	signInForm.setEmail("babatunde.adeniyi@dal.ca");
    	signInForm.setPassword("pass");
        BindException errors = new BindException(signInForm, "signInForm");
        signInValidator.validate(signInForm, errors);
        assertTrue(errors.hasErrors());
    }
    
}
