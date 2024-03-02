package com.izakaya.website.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.izakaya.website.service.UserService;

@RestController
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify/{token}")
    public String verifyEmail(@PathVariable String token, RedirectAttributes redirectAttributes) {
        boolean result = userService.verifyEmail(token, redirectAttributes);
        if (result) {
            return "redirect:/login"; // 회원가입이 성공적으로 완료되었을 때 로그인 페이지로 리다이렉트
        } else {
            return "redirect:/signup"; // 회원가입 인증에 실패했을 때 회원가입 페이지로 리다이렉트
        }
    }
}
