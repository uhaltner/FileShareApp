package com.group2.FileShare;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
	
    @RequestMapping("/")
    public String index() {
        return "Hello world!";
    }
}
