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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordRecoveryController {

    private static final Logger logger = LogManager.getLogger(PasswordRecoveryController.class);

    @GetMapping("/forgotpassword")
    public String passwordRecovery(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        ISignUpDAO signUpDAO = new SignUpDAO();
        IPasswordDAO passwordDAO = new PasswordDAO();
        IPasswordGenerator passwordGenerator = new PasswordGenerator();

        String email = request.getParameter("emailInput");
        String rawNewPassword = "";

        try {
            if (signUpDAO.userExist(email)) {

                rawNewPassword = passwordGenerator.generate();
                passwordDAO.updateRecoveryPassword(email, rawNewPassword);

                IMail recoveryMail = new PasswordRecoveryMail(email, rawNewPassword);
                new MailService().sendEmail(recoveryMail);

                logger.log(Level.INFO, "Email: " + email + " has successfully reset their password.");
                redirectAttributes.addFlashAttribute("PasswordResetNotification", "Your password has been reset, please check your email");

            } else {
                logger.log(Level.WARN, "Email: " + email + " does not exist, failed password recovery attempt");
                redirectAttributes.addFlashAttribute("PasswordResetNotification", "This email does not exist, please try again");
            }

        } catch (Exception e) {
            logger.log(Level.ERROR, "Email Input: " + email + " Problem with password recovery.", e);
            redirectAttributes.addFlashAttribute("PasswordResetNotification", "Your password can not be reset at this time, please try again later");
        }

        return "redirect:/login";
    }

}
