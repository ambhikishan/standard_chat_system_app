package com.example.StandardChatSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPage {

//    @GetMapping("/")
//    public String serveWebPage()
//    {
//        return "index";
//    }

    @GetMapping(value = "/{path:[^\\.]*}")
    public String forward() {
        return "forward:/index.html";
    }
}
