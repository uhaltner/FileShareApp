package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.*;
import com.group2.FileShare.User.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profileForm(HttpSession session, Model model){

        int userId = 1; // = session.getAttribute("userId");

        UserModel userModel = new UserModel();

        model.addAttribute("userFirstName", userModel.pullFirstName(userId));
        model.addAttribute("userLastName", userModel.pullLastName(userId));
        model.addAttribute("userEmail", userModel.pullEmail(userId));
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
        PasswordRuleSet passwordRules = new PasswordRuleSet();
        PasswordEncoder passwordEncoder = new PasswordEncoder();

        boolean validPassword = false;

        int userId = 1;// = session.getAttribute("userId");
        String updatedPassword = passwordForm.getPassword();
        String updatedPasswordConfirm = passwordForm.getConfirmPassword();

        //Check validity of password
        try{
            validPassword = passwordValidator.validatePassword(updatedPassword, updatedPasswordConfirm, passwordRules.getRules());

        }catch(Exception e) {
            System.err.println(e);
        }

        //valid password needs to be encoded and stored
        if(validPassword){

            //encode password
            String hashedPassword = passwordEncoder.hashPassword(updatedPassword);

            //store password in DB
            UserModel userModel = new UserModel();
            userModel.pushPassword(userId, hashedPassword);

            return "dashboard";

        }else{

            return "redirect:/profile";
        }

    }


}
