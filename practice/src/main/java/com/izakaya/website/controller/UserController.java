package com.izakaya.website.controller;

import com.izakaya.website.model.User;
import com.izakaya.website.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    // 메뉴 이동
    @GetMapping("menu")
    public String menu() {
        return "/menu.html";
    }

    // 점포정보 이동
    @GetMapping("about")
    public String about() {
        return "/about.html";
    }

    // QnA(게시판) 이동
    @GetMapping("contact")
    public String create() {
        return "/contact.html";
    }

    // 회원가입 페이지
    @GetMapping("join")
    public String joinForm(Model model) {
        model.addAttribute("user", new User());
        return "user/join";
    }

 // 회원가입 요청 처리
    @PostMapping("join")
    public String join(@ModelAttribute @Validated User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 유효성 검사 실패 시 다시 회원가입 폼으로 이동
        if (bindingResult.hasErrors()) {
            return "user/join";
        }

        // 이메일 중복 확인
        boolean isEmailDuplicate = userService.checkEmailDuplicate(user.getEmail());
        if (isEmailDuplicate) {
            // 중복된 이메일 처리
            // 예를 들어 알림창을 띄우거나 다른 방식으로 사용자에게 알림을 줄 수 있습니다.
        	redirectAttributes.addFlashAttribute("message", "중복된 이메일입니다.");
        	return "redirect:/EmailCheck";

        }

        userService.create(user);
        log.info("user: {}", user);
        return "redirect:/CheckEmail"; // 회원가입 성공 시 CheckEmail.html 페이지로 이동
    }


    // 로그인 페이지
    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User loginResult = userService.login(user);
        if (loginResult != null) {
            session.setAttribute("loginUser", loginResult);
            log.info("loginUser: {}", loginResult);
            return "redirect:/closePopup"; // 로그인 성공 시 팝업 창을 닫는 페이지로 이동
        } else {
            redirectAttributes.addFlashAttribute("error", "メールアドレスまたはパスワードが正しくありません。");
            return "redirect:/login";
        }
    }

    // 로그아웃 요청 처리
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 유저 리스트 페이지
    @GetMapping("list")
    public String findAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    // 이메일 인증 요청 처리
    @GetMapping("verify")
    public String verifyEmail(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        if (userService.verifyEmail(token, redirectAttributes)) {
            return "redirect:/closePopupJoin"; // 이메일 인증 성공 시 로그인 페이지로 리다이렉트
        } else {
            return "user/verification_fail"; // 이메일 인증 실패 시 이메일 인증 실패 페이지로 리다이렉트
        }
    }
}
