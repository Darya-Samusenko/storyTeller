package com.example.storyteller.controllers;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controllerMainPage {
    @GetMapping("/")
    public String works(){
         String res = "main";
         return res;
    }
}
