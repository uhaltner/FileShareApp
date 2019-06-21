package com.group2.FileShare.SignUp;

import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleSet;
import com.group2.FileShare.SignIn.SignInForm;
import com.group2.FileShare.ProfileManagement.PasswordValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class SignUpController {

    @GetMapping("/signup")
    public String profileForm(HttpSession session, Model model){
        model.addAttribute("signInForm", new SignInForm());
        model.addAttribute("signupForm", new SignUpForm());

        return "landing";
    }

    @PostMapping("/signup")
    public String signUpUser(@ModelAttribute SignUpForm signupForm){

        PasswordValidator passwordValidator = new PasswordValidator();
        SignUpModel signupModel = new SignUpModel();

        boolean validPassword = false;

        String formFirstName = signupForm.getFirstName();
        String formLastName = signupForm.getLastName();
        String formEmail = signupForm.getEmail();
        String formRawPassword = signupForm.getPassword();
        String formRawConfirmPassword = signupForm.getConfirmPassword();

        if( signupModel.userExist(formEmail) == false ){

            validPassword = passwordValidator.validatePassword( formRawPassword, formRawConfirmPassword, PasswordRuleSet.getRules() );

            if(validPassword) {

                signupModel.createProfile(formFirstName, formLastName, formEmail, formRawPassword);
                return "dashboard";
            }

        }

        //has to be updated later to '/landing' or whatever we are calling the landing page
        return "redirect:/signup";
    }




}
