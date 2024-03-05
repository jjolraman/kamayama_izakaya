package com.izakaya.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckEmailController {

    @GetMapping("/CheckEmail")
    public String showCheckEmailPage() {
        return "CheckEmail"; // CheckEmail.html 페이지를 반환
    }
}
