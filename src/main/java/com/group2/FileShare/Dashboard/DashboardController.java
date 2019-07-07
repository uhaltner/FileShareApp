package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.Dashboard.SortStrategy.*;
import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private AuthenticationSessionManager sessionManager;
    private DocumentSorter documentSorter;
    private boolean searchRequired = false;
    private String searchPhrase = "";
    
    @GetMapping("")
    public String dashBoard(HttpSession session, Model model)
    {
        try
        {
            sessionManager = AuthenticationSessionManager.instance();

            String firstName = sessionManager.getFirstName();
            String lastName = sessionManager.getLastName();
            int userId = sessionManager.getUserId();
            boolean publicDashboard = false;

            boolean isUserLoggedIn = sessionManager.isUserLoggedIn();

            //by default the list is sorted by creation datetime
            if(documentSorter == null) {
                documentSorter = new DocumentSorter(new CreatedSortStrategy());
            }

            List<Document> documentList = new ArrayList<>();
            documentList = DocumentController.getDocumentCollection(documentSorter, userId, publicDashboard);

            if(searchRequired){
                SearchBarIterator searchBarIterator = new SearchBarIterator();
                documentList = searchBarIterator.findAll(searchPhrase, documentList);
                searchRequired = false;
            }

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

    @GetMapping("/sort/name")
    public String sortByFileName() {

        documentSorter.changeStrategy(new NameSortStrategy());
        return "redirect: /dashboard";
    }

    @GetMapping("/sort/size")
    public String sortByFileSize() {

        documentSorter.changeStrategy(new SizeSortStrategy());
        return "redirect: /dashboard";
    }

    @GetMapping("/sort/type")
    public String sortByFileType() {

        documentSorter.changeStrategy(new FiletypeSortStrategy());
        return "redirect: /dashboard";
    }

    @GetMapping("/sort/modified")
    public String sortByFileModifiedTimeStamp() {

        documentSorter.changeStrategy(new ModifiedSortStrategy());
        return "redirect: /dashboard";
    }

    @PostMapping("/search")
    public String searchPhrase(HttpServletRequest request) {

        this.searchRequired = true;
        this.searchPhrase = request.getParameter("searchPhrase");

        return "redirect: /dashboard";
    }

}
