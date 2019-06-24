package com.group2.FileShare.SignUp;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleSet;
import com.group2.FileShare.SignIn.SignInForm;
import com.group2.FileShare.ProfileManagement.PasswordValidator;
import com.group2.FileShare.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SignUpController {

//    @RequestMapping(value = "/signup", method = GET)
//    public String profileForm(HttpSession session, Model model){
//
//        model.addAttribute("signInForm", new SignInForm());
//        model.addAttribute("signupForm", new SignUpForm());
//
//        return "landing";
//    }

    @RequestMapping(value = "/signup", method = POST)
    public String signUpUser(@ModelAttribute SignUpForm signupForm, HttpSession session, Model model){

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

                int userId = signupModel.createProfile(formFirstName, formLastName, formEmail, formRawPassword);

                User user = new User(userId, formEmail, formFirstName, formLastName);

                AuthenticationSessionManager.instance().setSession(user, session);

                return "redirect:/dashboard";
            }

        }

        return "redirect:/login";
    }




}
