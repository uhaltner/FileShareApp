package com.group2.FileShare.Dashboard;

import com.group2.FileShare.DefaultProperties;
import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.document.DocumentController;
import com.group2.FileShare.document.SharedLink;
import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


@Controller
@RequestMapping("/shared")
public class GuestDashboardController {

    private AuthenticationSessionManager sessionManager;
    private DocumentDAO documentDAO;
    private static final Logger logger = LogManager.getLogger(DefaultProperties.class);

    public GuestDashboardController() {
        sessionManager = AuthenticationSessionManager.instance();
        documentDAO = new DocumentDAO();
    }

    @GetMapping("")
    public String sharedFile() {

        return "redirect: dashboard";
    }

    @GetMapping("/{access_url}")
    public String sharedFile(Model model, @PathVariable String access_url) {

        model.addAttribute("isLoggedIn", sessionManager.isUserLoggedIn());
        SharedLink sharedDocRef = documentDAO.getLinkedDocumentRefWith(access_url);

        if(sharedDocRef != null) 
        {
            try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date expDate = dateFormat.parse(sharedDocRef.getExpiration_date());
                    Date currentDate = new Date();
                    if(expDate.after(currentDate)) 
                    {

                        Document document = DocumentController.getGuestDocument(sharedDocRef.getDocument_id());
                        if(document != null) 
                        {
                            model.addAttribute("document", document);
                        } 
                        else 
                        {
                            model.addAttribute("error", "No document found with the provided link");
                        }
                    } 
                    else 
                    {
                        model.addAttribute("error", "Sorry, the link has expired");
                    }
                }
            catch(ParseException ex) 
            {
                model.addAttribute("error", "Can't access the file at the moment, please try again later");
                ex.printStackTrace();
                logger.log(Level.ERROR, "Error parsing expiration date in sharedFile()" , ex.getMessage());
            }
        } 
        else 
        {
            model.addAttribute("error", "This link doesn't exist in Fileshare App");
        }
        return "guest_dashboard";
    }
}
