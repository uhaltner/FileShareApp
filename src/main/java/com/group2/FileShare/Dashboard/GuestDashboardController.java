package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.document.SharedLink;
import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentDAO;
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

        if(sharedDocRef != null) {
            try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date expDate = dateFormat.parse(sharedDocRef.getExpiration_date());
                    Date currentDate = new Date();
                    if(expDate.after(currentDate)) {

                        Document document = documentDAO.getDocument(sharedDocRef.getDocument_id());
                        if(document != null) {
                            model.addAttribute("document", document);
                        } else {
                            model.addAttribute("error", "No document found with the provided link");
                            //System.out.println("No document found with the provided link");
                        }
                    } else {
                        model.addAttribute("error", "Sorry, the link has expired");
                        //System.out.println("Sorry, the link has expired");
                    }
                }
            catch(ParseException ex) {
                System.out.println(ex.getMessage());
                model.addAttribute("error", "Can't access the file at the moment, please try again later");
                ex.printStackTrace();
            }
        } else {
            model.addAttribute("error", "This link doesn't exist in Fileshare App");
            //System.out.println("This link doesn't exist in Fileshare App");
        }
        return "guest_dashboard";
    }
}
