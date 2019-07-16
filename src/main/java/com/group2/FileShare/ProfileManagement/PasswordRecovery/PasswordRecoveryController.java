package com.group2.FileShare.ProfileManagement.PasswordRecovery;

import com.group2.FileShare.ProfileManagement.IPasswordDAO;
import com.group2.FileShare.ProfileManagement.PasswordDAO;
import com.group2.FileShare.SignUp.ISignUpDAO;
import com.group2.FileShare.SignUp.SignUpDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordRecoveryController {

    @GetMapping("/forgotpassword")
    public String passwordRecovery(HttpServletRequest request) {

        String email = request.getParameter("emailInput");
        String rawNewPassword = "";

        ISignUpDAO signUpDAO = new SignUpDAO();
        IPasswordDAO passwordDAO = new PasswordDAO();
        IPasswordGenerator passwordGenerator = new PasswordGenerator();

        System.out.println("[ForgotPassword] Entered the email: " + email);
        System.out.println("[ForgotPassword] New Password: " + rawNewPassword);

       if(signUpDAO.userExist(email)){

           //generate new password
           rawNewPassword = passwordGenerator.generate();

            //update new password
           //passwordDAO.updateRecoveryPassword(email, rawNewPassword);

           //send email;
           IMail recoveryMail = new PasswordRecoveryMailMessage(email,rawNewPassword);

           try{
               //sendEmail(recoveryMail);
               new MailService().sendEmail(recoveryMail);

           }catch (Exception e){
               System.out.println("[MailError]");
               e.printStackTrace();
           }

           //todo: notify user that password has been sent to email

       }else{

           //todo: user does not exist - tell user email is not valid
       }

        return "redirect:/login";
    }

}
