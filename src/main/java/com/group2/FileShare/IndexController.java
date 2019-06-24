package com.group2.FileShare;

import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error() {
		return "redirect:/dashboard";
	}

    @GetMapping(value = {"/", ""})
    public String profileForm(HttpSession session, Model model)
    {
    	return "redirect:/dashboard";
    }
    
	@Override
	public String getErrorPath() {
		return PATH;
	}
}
