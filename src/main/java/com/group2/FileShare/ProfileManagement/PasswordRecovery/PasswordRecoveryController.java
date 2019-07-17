package com.group2.FileShare.ProfileManagement.PasswordRecovery;

import com.group2.FileShare.MailService;
import com.group2.FileShare.ProfileManagement.IPasswordDAO;
import com.group2.FileShare.ProfileManagement.PasswordDAO;
import com.group2.FileShare.SignUp.ISignUpDAO;
import com.group2.FileShare.SignUp.SignUpDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordRecoveryController {

    private static final Logger logger = LogManager.getLogger(PasswordRecoveryController.class);

    @GetMapping("/forgotpassword")
    public String passwordRecovery(HttpServletRequest request, Model model) {

        String email = request.getParameter("emailInput");
        String rawNewPassword = "";

        ISignUpDAO signUpDAO = new SignUpDAO();
        IPasswordDAO passwordDAO = new PasswordDAO();
        IPasswordGenerator passwordGenerator = new PasswordGenerator();

       if(signUpDAO.userExist(email)){

           //generate new password
           rawNewPassword = passwordGenerator.generate();
           IMail recoveryMail = new PasswordRecoveryMailMessage(email,rawNewPassword);

           try {
               //update new password in database
               passwordDAO.updateRecoveryPassword(email, rawNewPassword);

           }catch (Exception e) {
               logger.log(Level.ERROR, "Email: "+ email +" Problem updating recovery password in database.",e);

               //todo: notify UI there is a problem with email recovery, try again later.
               //model.addAttribute("PasswordResetNotification","Your password can not be reset at this time, please try again later");

               return "redirect:/login";
           }

           try{
                //send email;
                new MailService().sendEmail(recoveryMail);

           }catch (Exception e){
               logger.log(Level.ERROR, "Email: "+ email +" Problem sending password recovery email.", e);

               //todo: notify UI there is a problem with email recovery, try again later.
               //model.addAttribute("PasswordResetNotification","Your password can not be reset at this time, please try again later");

               return "redirect:/login";
           }
           logger.log(Level.INFO, "Email: "+ email +" has reset their password.");

           //todo: notify UI that password has been successfully reset, please check your email
           //model.addAttribute("PasswordResetNotification","Your password has been reset, please check your email");


       }else{
           logger.log(Level.WARN, "Email: "+ email +" does not exist, failed password recovery attempt");

           //todo: notify UI that user does not exist
           //model.addAttribute("PasswordResetNotification","This email does not exist, please try again");
       }

        return "redirect:/login";
    }

}
