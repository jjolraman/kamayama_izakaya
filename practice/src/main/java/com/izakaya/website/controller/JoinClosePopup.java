package com.izakaya.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JoinClosePopup {

    @GetMapping("/closePopupJoin")
    public String closePopup() {
        return "close_popupJoin"; 
    }
}
