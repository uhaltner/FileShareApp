package com.group2.FileShare;

import javax.servlet.http.HttpSession;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.SignIn.SignInForm;
import com.group2.FileShare.SignUp.SignUpForm;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error(Model model) {
		return "redirect:/dashboard";
	}

    @GetMapping(value = {"/", ""})
    public String profileForm(HttpSession session, Model model)
    {
		if (AuthenticationSessionManager.instance().isUserLoggedIn()) {
			return "redirect:/dashboard";
		} else {
			model.addAttribute("signInForm", new SignInForm());
			model.addAttribute("signupForm", new SignUpForm());
			return "landing";
		}
    }

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
