package com.izakaya.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClosePopupController {

    @GetMapping("/closePopup")
    public String closePopup() {
        return "close_popup"; 
    }
}
