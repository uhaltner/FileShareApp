package com.group2.FileShare.SignIn;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class SignInValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
	    return SignInForm.class.equals(aClass);
	}

	@Override
    public void validate(Object o, Errors errors) {
		SignInForm signInForm = (SignInForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!isValidEmailAddress(signInForm.getEmail())) {
            errors.rejectValue("email", "Invalid.signInForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (signInForm.getPassword().length() < 8) {
            errors.rejectValue("password", "Size.signInForm.password");
        }
     }
	
    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
