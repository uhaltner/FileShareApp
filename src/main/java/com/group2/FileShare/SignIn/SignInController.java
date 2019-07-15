package com.group2.FileShare.SignIn;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.ProfileManagement.PasswordEncoder;
import com.group2.FileShare.SignUp.SignUpForm;
import com.group2.FileShare.User.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SignInController {
	
	private AuthenticationSessionManager authSessionManager;
	private static final Logger logger = LogManager.getLogger(SignInController.class);

	public SignInController() {
		authSessionManager = AuthenticationSessionManager.instance();
	}
	
    @GetMapping("/login")
    public String profileForm(Model model){

        model.addAttribute("signInForm", new SignInForm());
        model.addAttribute("signupForm", new SignUpForm());
        return "landing";
    }
	
    @PostMapping("/login")
    public String registration(@Valid SignInForm signInForm, BindingResult bindingResult, HttpSession session,  Model model) {
    	
    	SignInValidator signInValidator = new SignInValidator();
    	signInValidator.validate(signInForm, bindingResult);
    	 
        if (!bindingResult.hasErrors())
        {
        	PasswordEncoder passwordEncoder = new PasswordEncoder();
        	SignInDAO signInDao = new SignInDAO();
        	User user = signInDao.getUserWith(signInForm, passwordEncoder);

        	if(user != null) {
    			authSessionManager.setSession(user, session);
				logger.log(Level.INFO, "User: "+ user.getId() +"  login successfully");
    		    return "redirect:/dashboard";
    		} else {
            	model.addAttribute("login_error", "UserNotFound.signForm");
				logger.log(Level.ERROR, "Error with user login of email: "+signInForm.getEmail() +" at registration()");

			}
        }
		model.addAttribute("signInForm", signInForm);
		model.addAttribute("signupForm", new SignUpForm());
        return "landing";
    }
    
    @GetMapping("/signout")
    public String logout(HttpSession session){
		logger.log(Level.INFO, "User: "+authSessionManager.getUserId() +" logout successfully");
    	authSessionManager.destroySession();
    	return "redirect:/login";
    }

}
