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
@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping()
    public String main() {
        return "index";
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
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute User user, HttpSession session) {
        User loginResult = userService.login(user);
        if (loginResult != null) {
            session.setAttribute("loginUser", loginResult);
            log.info("loginUser: {}", loginResult);
            return "redirect:/";
        } else {
            return "user/login";
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

    @GetMapping("list/{id}")
    public String findbyId(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/detail";
    }

    @GetMapping("update/{id}")
    public String updateForm(@PathVariable(name = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("update/{id}")
    public String update(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/list/{id}";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/list";
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
