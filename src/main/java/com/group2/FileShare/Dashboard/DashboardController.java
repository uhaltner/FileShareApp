package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DashboardController {

    private AuthenticationSessionManager sessionManager;
    
    @GetMapping(value = {"/dashboard" })
    public String dashBoard(HttpSession session, Model model)
    {
        try
        {
            sessionManager = AuthenticationSessionManager.instance();

            String firstName = sessionManager.getFirstName();
            String lastName = sessionManager.getLastName();
            boolean isUserLoggedIn = sessionManager.isUserLoggedIn();
            List<Document> documentList = DocumentController.getDocumentCollection();
            model.addAttribute("documents", documentList);
            model.addAttribute("firstName",firstName);
            model.addAttribute("lastName",lastName);

            if (isUserLoggedIn) {
                return "dashboard";
            } else {
                return "landing";
            }
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }
        return "landing";
    }

}
