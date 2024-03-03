package com.izakaya.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.izakaya.website.service.UserService;

@Controller
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify/{token}")
    public String verifyEmail(@PathVariable String token, RedirectAttributes redirectAttributes) {
        boolean result = userService.verifyEmail(token, redirectAttributes);
        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 성공적으로 완료되었습니다. 이제 로그인해주세요!");
            // 인증 메일이 발송되면 팝업 창을 닫기 위해 JavaScript를 호출
            redirectAttributes.addFlashAttribute("closePopup", true);
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입 인증에 실패했습니다. 유효하지 않은 토큰입니다.");
            return "redirect:/join";
        }
    }
}
