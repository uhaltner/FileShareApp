package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.ProfileManagement.PasswordRules.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    private AuthenticationSessionManager sessionManager;

    @GetMapping("/profile")
    public String profileForm(HttpSession session, Model model){

        sessionManager = AuthenticationSessionManager.instance();

        String firstName = sessionManager.getFirstName();
        String lastName = sessionManager.getLastName();
        String email = sessionManager.getEmail();

        model.addAttribute("userFirstName", firstName);
        model.addAttribute("userLastName", lastName);
        model.addAttribute("userEmail", email);
        model.addAttribute("passwordForm", new PasswordForm());

        return "profile";
    }

    @PostMapping(value="/profile", params = "action=cancel")
    public String cancelProfile(@ModelAttribute PasswordForm passwordForm){

        return "dashboard";
    }

    @PostMapping(value="/profile", params = "action=update")
    public String updateProfile(@ModelAttribute PasswordForm passwordForm){

        PasswordValidator passwordValidator = new PasswordValidator();
        PasswordModel passwordModel = new PasswordModel();

        boolean validPassword = false;

        int userId = sessionManager.getUserId();

        String updatedPassword = passwordForm.getPassword();
        String updatedPasswordConfirm = passwordForm.getConfirmPassword();

        //Check validity of password
        try{
            validPassword = passwordValidator.validatePassword(updatedPassword, updatedPasswordConfirm, PasswordRuleSet.getRules());

        }catch(Exception e) {
            System.err.println(e);
        }

        if(validPassword){

            passwordModel.updatePassword(userId, updatedPassword);

            return "dashboard";

        }else{

            return "redirect:/profile";
        }

    }


}
