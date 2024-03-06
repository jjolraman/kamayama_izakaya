package com.izakaya.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailCheckController {
	@GetMapping("/EmailCheck")
    public String closePopup() {
        return "EmailCheck"; 
    }
}
