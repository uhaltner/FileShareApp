package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleSet;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    private AuthenticationSessionManager sessionManager;
    private boolean profile_error = false;
    private static final Logger logger = LogManager.getLogger(ProfileController.class);


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

        if(profile_error == true){
            model.addAttribute("profile_error", "Invalid password, please try again.");
            logger.log(Level.WARN, "[User:"+sessionManager.getUserId()+"] failed to validate password");
            profile_error = false;
        }

        return "profile";
    }

    @PostMapping(value="/profile", params = "action=cancel")
    public String cancelProfile(@ModelAttribute PasswordForm passwordForm){
        return "redirect:/dashboard";
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
        try
        {
            validPassword = passwordValidator.validatePassword(updatedPassword, updatedPasswordConfirm, PasswordRuleSet.getRules());

        }
        catch(Exception e) {
            logger.log(Level.ERROR, "Failed to validate password at updateProfile(): ", e);
        }

        if(validPassword){

            passwordModel.updatePassword(userId, updatedPassword);

            return "redirect:/dashboard";

        }else{

            profile_error = true;
            return "redirect:/profile";
        }

    }


}
