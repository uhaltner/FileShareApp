package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRuleSet;
import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleSet;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private AuthenticationSessionManager sessionManager;
    private boolean profile_error = false;
    private static final Logger logger = LogManager.getLogger(ProfileController.class);


    @GetMapping("/profile")
    public String profileForm(Model model){

        sessionManager = AuthenticationSessionManager.instance();

        model.addAttribute("userFirstName", sessionManager.getFirstName());
        model.addAttribute("userLastName", sessionManager.getLastName());
        model.addAttribute("userEmail", sessionManager.getEmail());
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

        IPasswordValidator passwordValidator = new PasswordValidator();
        IPasswordDAO passwordDAO = new PasswordDAO();
        IPasswordRuleSet passwordRuleSet = new PasswordRuleSet();

        int userId = sessionManager.getUserId();

        String updatedPassword = passwordForm.getPassword();
        String updatedPasswordConfirm = passwordForm.getConfirmPassword();

        boolean validPassword = false;

        //Check validity of password
        validPassword = passwordValidator.validatePassword(updatedPassword, updatedPasswordConfirm, passwordRuleSet.getRules());

        if(validPassword){
            passwordDAO.updatePassword(userId, updatedPassword);
            return "redirect:/dashboard";

        }else{
            profile_error = true;
            return "redirect:/profile";
        }
    }
    
}
