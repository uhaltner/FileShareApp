package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DashboardController {

    private AuthenticationSessionManager sessionManager;
    private DocumentDAO documentDAO;

    @GetMapping("/dashboard")
    public String profileForm(HttpSession session, Model model)
    {
        try
        {
            documentDAO = new DocumentDAO();
            sessionManager = AuthenticationSessionManager.instance();

            String firstName = sessionManager.getFirstName();
            String lastName = sessionManager.getLastName();
            boolean isUserLoggedIn = sessionManager.isUserLoggedIn();
            List<Document> documentList = documentDAO.getDocuments();
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
