package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.document.DocumentDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private AuthenticationSessionManager sessionManager;
    private DocumentDAO documentDAO;

    @GetMapping("/dashboard")
    public String profileForm(HttpSession session, ModelAndView modelAndView, Model model)
    {
        documentDAO = new DocumentDAO();
        sessionManager = AuthenticationSessionManager.instance();

        String firstName = sessionManager.getFirstName();
        String lastName = sessionManager.getLastName();
        boolean isUserLoggedIn = sessionManager.isUserLoggedIn();
        //List<Document> documentList = documentDAO.getDocuments();
        //modelAndView.addObject("documents", documentList);
        model.addAttribute("firstName",firstName);
        model.addAttribute("lastName",lastName);

        if (isUserLoggedIn) {
            return "dashboard";
        } else {
            return "landing";
        }
    }

}
