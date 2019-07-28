package com.group2.FileShare.ProfileManagement.PasswordRecovery;

import com.group2.FileShare.IMailService;
import com.group2.FileShare.MailService;
import com.group2.FileShare.ProfileManagement.IPasswordDAO;
import com.group2.FileShare.ProfileManagement.PasswordDAO;
import com.group2.FileShare.SignUp.ISignUpDAO;
import com.group2.FileShare.SignUp.SignUpDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordRecoveryController {

    private static final Logger logger = LogManager.getLogger(PasswordRecoveryController.class);

    public enum Feedback{
        OK,
        INVALID_USER,
        ERROR,
    }

    @GetMapping("/forgotpassword")
    public String passwordRecovery(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        ISignUpDAO signUpDAO = new SignUpDAO();
        IPasswordDAO passwordDAO = new PasswordDAO();
        IMailService mailService= new MailService();

        String email = request.getParameter("emailInput");

        Feedback feedback = passwordRecovery(signUpDAO, passwordDAO, mailService, email);
        redirectAttributes.addFlashAttribute("PasswordResetNotification", getNotification(feedback));

        return "redirect:/login";
    }

    public Feedback passwordRecovery(ISignUpDAO signUpDAO, IPasswordDAO passwordDAO, IMailService mailService, String email){

        IPasswordGenerator passwordGenerator = new PasswordGenerator();
        Feedback feedback;
        String rawNewPassword = "";

        try {
            if (signUpDAO.userExist(email)) {

                //generate and update password
                rawNewPassword = passwordGenerator.generate();
                passwordDAO.updateRecoveryPassword(email, rawNewPassword);

                //create and send recovery email
                IMail recoveryMail = new PasswordRecoveryMail(email, rawNewPassword);
                mailService.sendEmail(recoveryMail);

                logger.log(Level.INFO, "Email: " + email + " has successfully reset their password.");
                feedback = Feedback.OK;

            } else {
                logger.log(Level.WARN, "Email: " + email + " does not exist, failed password recovery attempt");
                feedback = Feedback.INVALID_USER;
            }

        } catch (Exception e) {
            logger.log(Level.ERROR, "Email Input: " + email + " Problem with password recovery.", e);
            feedback = Feedback.ERROR;
        }

        return feedback;
    }

    private String getNotification(Feedback feedback){

        switch (feedback){
            case OK:
                return "Your password has been reset, please check your email";
            case INVALID_USER:
                return "This email does not exist, please try again";
            case ERROR:
                return "Your password can not be reset at this time, please try again later";
        }

        return "";

    }

}
