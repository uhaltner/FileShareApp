package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.User.IUser;
import com.group2.FileShare.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

        return "dashboard";
    }


    @PostMapping(value="/profile", params = "action=update")
    public String updateProfile(@ModelAttribute PasswordForm passwordForm){

        String newPassword = passwordForm.getPassword();
        String newPasswordConfirm = passwordForm.getConfirmPassword();

        System.out.println("Update");
        System.out.println("Password: " + newPassword);
        System.out.println("ConfirmPassword: " +newPasswordConfirm);

        if(newPassword.equals(newPasswordConfirm) == false){
            System.out.println("Passwords do not match");
            return "redirect:/profile";
        }

        return "dashboard";
    }


}
