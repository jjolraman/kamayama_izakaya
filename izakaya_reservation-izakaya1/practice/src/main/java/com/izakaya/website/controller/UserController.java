// UserController.java

package com.izakaya.website.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.izakaya.website.model.User;
import com.izakaya.website.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    
    @GetMapping("test")
	public String test() {
		return "/test.html";
	}
    
    /*메뉴 이동*/
	@GetMapping("menu")
	public String menu() {
		return "/menu.html";
	}
	
	 /*점포정보 이동*/
	@GetMapping("about")
	public String about() {
		return "/about.html";
	}
	
	 /*QnA(게시판) 이동*/
	@GetMapping("contact")
	public String create() {
		return "/contact.html";
	}

    @GetMapping("join")
    public String joinForm(Model model) {
        model.addAttribute("user", new User());
        return "user/join";
    }

    @PostMapping("join")
    public String join(@ModelAttribute User user) {
        userService.create(user);
        log.info("user: {}", user);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User loginResult = userService.login(user);
        if (loginResult != null) {
            session.setAttribute("loginUser", loginResult);
            log.info("loginUser: {}", loginResult);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "redirect:/login";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("list")
    public String findAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("verify")
    public String verifyEmail(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        if (userService.verifyEmail(token, redirectAttributes)) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        } else {
            return "user/verification_fail"; // 이메일 인증 실패 페이지로 리다이렉트
        }
    }
}
