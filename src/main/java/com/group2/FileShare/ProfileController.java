package com.group2.FileShare;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    //@RequestMapping(value = "/", method = RequestMethod.GET)
    @RequestMapping("/profile")
    public String profile(HttpServletRequest request, Model model){
        /*
        //get name parameter from request; will be null if no parameter passed in
        String name = request.getParameter("name");

        if(name == null){
            name = "world";
        }
        model.addAttribute("message", name);
        */

        String firstName = "John";
        String lastName = "Smith";
        String email = "john.smith@email.com";
        String action = "";

        model.addAttribute("userFirstName", firstName);
        model.addAttribute("userLastName", lastName);
        model.addAttribute("userEmail", email);

        model.addAttribute("result", action);

        return "profile";
    }

    @RequestMapping("/feedback")
    public void profileSubmit(String action){

        System.out.println("Feedback: ");
    }

}
