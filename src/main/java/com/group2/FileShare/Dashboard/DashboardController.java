package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.Dashboard.DashboardStrategy.Dashboard;
import com.group2.FileShare.Dashboard.DashboardStrategy.PrivateDashboard;
import com.group2.FileShare.Dashboard.DashboardStrategy.PublicDashboard;
import com.group2.FileShare.Dashboard.DashboardStrategy.TrashDashboard;
import com.group2.FileShare.Dashboard.SortStrategy.*;
import com.group2.FileShare.document.DeleteObserver.DeleteDocument;
import com.group2.FileShare.document.Document;
import com.group2.FileShare.document.DocumentController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private AuthenticationSessionManager sessionManager;
    private DocumentSorter documentSorter = new DocumentSorter(new CreatedSortStrategy());
    private Dashboard currentDashboard = new Dashboard(new PrivateDashboard());
    private SearchBarHandler searchBarHandler = new SearchBarHandler();

    private static final Logger logger = LogManager.getLogger(DashboardController.class);

    @GetMapping("")
    public String dashBoard(Model model)
    {
        List<Document> documentList = new ArrayList<>();

        sessionManager = AuthenticationSessionManager.instance();
        int userId = sessionManager.getUserId();

        try {
            documentList = DocumentController.getDocumentCollection(documentSorter, userId, currentDashboard);

            if(searchBarHandler.isSearchRequired()){
                documentList = DocumentController.findAll(searchBarHandler.getSearchPhrase());
                searchBarHandler.reset();
            }

        }catch(Exception e) {
            logger.log(Level.ERROR, "Error occurred in Dashboard controller for user: " + userId , e);
        }

        model.addAttribute("documents", documentList);
        model.addAttribute("firstName", sessionManager.getFirstName() );
        model.addAttribute("lastName", sessionManager.getLastName() );

        return currentDashboard.getTemplate();
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

        searchBarHandler.setSearchRequired(true);
        searchBarHandler.setSearchPhrase(request.getParameter("searchPhrase"));

        return "redirect: /dashboard";
    }

    @GetMapping("/private")
    public String privateDashboard(){

        currentDashboard.changeDashboard(new PrivateDashboard());

        return "redirect: /dashboard";
    }

    @GetMapping("/public")
    public String publicDashboard(){

        currentDashboard.changeDashboard(new PublicDashboard());

        return "redirect: /dashboard";
    }

    @GetMapping("/trash")
    public String trashDashboard()
    {
        DeleteDocument.deleteDocumentPermanently();
        currentDashboard.changeDashboard(new TrashDashboard());

        return "redirect: /dashboard";
    }

}
