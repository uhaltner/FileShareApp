package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.document.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/shared")
public class GuestDashboardController
{
    private AuthenticationSessionManager sessionManager;
    private DocumentDAO documentDAO;
    private SharedDocumentLinkValidator sharedDocumentLinkValidator;

    public GuestDashboardController() {
        sessionManager = AuthenticationSessionManager.instance();
        documentDAO = new DocumentDAO();
        sharedDocumentLinkValidator = new SharedDocumentLinkValidator();
    }

    @GetMapping("")
    public String sharedFile()
    {
        return "redirect: dashboard";
    }

    @GetMapping("/{access_url}")
    public String sharedFile(Model model, @PathVariable String access_url)
    {
        model.addAttribute("isLoggedIn", sessionManager.isUserLoggedIn());
        SharedDocumentLink sharedDocRef = documentDAO.getLinkedDocumentRefWith(access_url);

        String errorResponse = sharedDocumentLinkValidator.validateDocumentLinkWithErrorResponse(sharedDocRef);
        if(errorResponse.equals("")) {
            Document document = DocumentController.getGuestDocument(sharedDocRef.getDocument_id());
            if(document != null)
            {
                model.addAttribute("document", document);
            }
            else
            {
                model.addAttribute("error", "No document found with the provided link");
            }
        } else {
            model.addAttribute("error", errorResponse);
        }

        return "guest_dashboard";
    }
}
