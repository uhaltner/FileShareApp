package com.group2.FileShare.Dashboard;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String profileForm(HttpSession session, Model model) {
        return "dashboard";
    }
    
}
