package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Authentication.AuthenticationSessionManager;
import com.group2.FileShare.Dashboard.SortStrategy.NameSortStrategy;
import com.group2.FileShare.document.Document;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**@Author: Ueli Haltner */
public class SortController {

    @GetMapping(value = {"/sort/name" })
    public String sortByName(HttpSession session, Model model)
    {
        int userId = AuthenticationSessionManager.instance().getUserId();
        boolean isPublic = false;
        List<Document> documentList = new ArrayList<>();

        DocumentSorter sorter = new DocumentSorter(new NameSortStrategy());
        documentList = sorter.executeStrategy(userId, isPublic);

        model.addAttribute("documents", documentList);

        return "dashboard";

    }


}
