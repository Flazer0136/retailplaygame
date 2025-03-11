package com.cpro.retailplaygame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/hello")
    public String sayHello(Model theModel) {

        return "helloworld";
    }
}
