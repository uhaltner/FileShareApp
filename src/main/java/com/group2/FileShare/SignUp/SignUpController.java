package com.group2.FileShare.SignUp;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.ProfileManagement.IPasswordValidator;
import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRuleDAO;
import com.group2.FileShare.ProfileManagement.PasswordRules.PasswordRuleDAO;
import com.group2.FileShare.ProfileManagement.PasswordValidator;
import com.group2.FileShare.User.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SignUpController {

    private static final Logger logger = LogManager.getLogger(SignUpController.class);

    @RequestMapping(value = "/signup", method = POST)
    public String signUpUser(@ModelAttribute SignUpForm signupForm, HttpSession session, RedirectAttributes redirectAttributes){

        IPasswordRuleDAO passwordRuleDAO = new PasswordRuleDAO();
        IPasswordValidator passwordValidator = new PasswordValidator(passwordRuleDAO);
        ISignUpDAO signUpDAO = new SignUpDAO();

        boolean validPassword = false;
        String returnTemplate = "redirect:/login";

        try{

            if(userExists(signupForm,signUpDAO) == false){

                validPassword = validatePassword(passwordValidator, signupForm);

                if(validPassword)
                {
                    int userId = createUser(signUpDAO, signupForm);

                    if(userId != -1){
                        setSessionUser(userId, session, signupForm);
                        logger.log(Level.INFO, "New user created in signUpUser() with userId: " + userId);
                        returnTemplate = "redirect:/dashboard";

                    }else{
                        logger.log(Level.WARN, "New user could not be created in signUpUser() ");
                    }

                }else{
                    redirectAttributes.addFlashAttribute("EmailError","Entered password is not valid, please try again.");
                }

            }else{
                redirectAttributes.addFlashAttribute("EmailError","Email already taken! Please try again with different email address.");
            }

        }catch (Exception e){
            logger.log(Level.ERROR, "Issue creating new user in signUpUser()",e);
            redirectAttributes.addFlashAttribute("EmailError","We are having troubles creating your account, please try again later.");
        }

        return returnTemplate;
    }


    private void setSessionUser(int userId, HttpSession session, SignUpForm signUpForm){

        User user = new User(userId, signUpForm.getEmail(), signUpForm.getFirstName(), signUpForm.getLastName());
        AuthenticationSessionManager.instance().setSession(user, session);

        return;
    }

    private boolean userExists(SignUpForm signUpForm, ISignUpDAO signUpDAO){
        return signUpDAO.userExist(signUpForm.getEmail());
    }

    private boolean validatePassword(IPasswordValidator passwordValidator, SignUpForm signUpForm){

        String formRawPassword = signUpForm.getPassword();
        String formRawConfirmPassword = signUpForm.getConfirmPassword();

        return passwordValidator.validatePassword( formRawPassword, formRawConfirmPassword);
    }

    private int createUser(ISignUpDAO signUpDAO, SignUpForm signUpForm){

        String formFirstName = signUpForm.getFirstName();
        String formLastName = signUpForm.getLastName();
        String formEmail = signUpForm.getEmail();
        String formRawPassword = signUpForm.getPassword();

        return signUpDAO.createProfile(formFirstName, formLastName, formEmail, formRawPassword);
    }

}
