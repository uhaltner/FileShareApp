package com.group2.FileShare.UserProfile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private boolean firstFormCall = true;

    @GetMapping("/profile")
    public String profileForm(HttpServletRequest request, Model model){

        System.out.println("Hello Profile Form");

        if(firstFormCall){

            System.out.println("Profile Form Call");

            firstName = "John";
            lastName = "Smith";
            email = "john.smith@email.com";

            firstFormCall = false;
        }


        model.addAttribute("userFirstName", firstName);
        model.addAttribute("userLastName", lastName);
        model.addAttribute("userEmail", email);

        model.addAttribute("passwordForm", new PasswordForm());

        return "profile";
    }

    @PostMapping(value="/profile", params = "action=cancel")
    public String cancelProfile(@ModelAttribute PasswordForm passwordForm){

        return "dashboardMock";
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

        return "dashboardMock";
    }


}
