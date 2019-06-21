package com.group2.FileShare.SignIn;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.SignUp.SignUpForm;
import com.group2.FileShare.User.User;

@Controller
public class SignInController {
	
	private AuthenticationSessionManager authSessionManager;

	public SignInController() {
		authSessionManager = AuthenticationSessionManager.instance();
	}
	
    @GetMapping("/login")
    public String profileForm(HttpSession session, Model model){

        model.addAttribute("signInForm", new SignInForm());
        model.addAttribute("signupForm", new SignUpForm());
        return "landing";
    }
	
    @PostMapping("/login")
    public String registration(@Valid SignInForm signInForm, BindingResult bindingResult, HttpSession session,  Model model) {
    	
    	SignInValidator signInValidator = new SignInValidator();
    	signInValidator.validate(signInForm, bindingResult);
    	 
        if (!bindingResult.hasErrors()) {
            SignInService signInService = new SignInService();
            User user = signInService.authenticateUserWith(signInForm);
    		if(user != null) {
    			authSessionManager.setUser(user, session);
    		    return "redirect:/dashboard";
    		} else {
            	model.addAttribute("login_error", "UserNotFound.signForm");
    		}
        }
        
    	model.addAttribute("signupForm", new SignUpForm());
        return "landing";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session){
    	authSessionManager.destroySession(session);
    	return "redirect:/login";
    }

}
