package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.*;
import com.group2.FileShare.User.IUser;
import com.group2.FileShare.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProfileController {

    private IUser user;
    private boolean firstFormCall = true;

    @GetMapping("/profile")
    public String profileForm(HttpSession session, Model model){


        //int userId = session.getAttribute("userId");
        int userId = 1;
        System.out.println("Hello Profile Form");

        if(firstFormCall){

            System.out.println("Profile Form Call");

            user = new User(userId);

            firstFormCall = false;
        }


        model.addAttribute("userFirstName", user.getFirstName());
        model.addAttribute("userLastName", user.getLastName());
        model.addAttribute("userEmail", user.getEmail());

        model.addAttribute("passwordForm", new PasswordForm());

        return "profile";
    }

    @PostMapping(value="/profile", params = "action=cancel")
    public String cancelProfile(@ModelAttribute PasswordForm passwordForm){

        return "dashboardMock";
    }

    @PostMapping(value="/profile", params = "action=update")
    public String updateProfile(@ModelAttribute PasswordForm passwordForm){

        boolean validPassword = false;

        String newPassword = passwordForm.getPassword();
        String newPasswordConfirm = passwordForm.getConfirmPassword();

        System.out.println("Update");
        System.out.println("Password: " + newPassword);
        System.out.println("ConfirmPassword: " +newPasswordConfirm);

        // Create password rules
        ArrayList<IPasswordRule> passwordRules= new ArrayList<>();

        passwordRules.add(new LengthRule(8,30));
        passwordRules.add(new LowercaseCharacterRule());
        passwordRules.add(new UppercaseCharacterRule());
        passwordRules.add(new NumericCharacterRule());

        //Create password validator
        PasswordValidator passwordValidator = new PasswordValidator();

        //Check validity of password
        validPassword = passwordValidator.validatePassword(newPassword, newPasswordConfirm, passwordRules);

        if(validPassword){

            //encode password

            //store password in DB

            return "dashboardMock";
        }else{

            System.out.println("Password not valid");
            return "redirect:/profile";
        }

    }


}
