package com.example.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.practice.model.User;
import com.example.practice.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {
	// 생성자 주입
	private final UserService userService;
	
	/*메인페이지*/
	@GetMapping()
	public String main() {
		return "main";
	}
	
	/*회원가입 페이지*/
	@GetMapping("join")
	public String joinForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "user/join";
	}
	
	/*회원가입*/
	@PostMapping("join")
	public String join(@ModelAttribute User user) {
		User findUser = userService.findByEmail(user.getEmail());
		
		// 아이디 중복 확인
		if(findUser != null) {
			return "user/join";
		}
		
		userService.create(user);
		return "redirect:/";
	}
	
	/*로그인 페이지*/
	@GetMapping("login")
	public String loginForm(Model model) {
		model.addAttribute("user", new User());
		return "user/login";
	}
	
	/*로그인*/
	@PostMapping("login")
	public String login(@ModelAttribute User user, HttpSession session) {
		User loginResult = userService.login(user);
		
		if(loginResult != null) {
			session.setAttribute("loginUser", loginResult);
			return "redirect:/";
		} else {
			return "user/login";
		}
	}
	
	/*로그아웃*/
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	/*회원탈퇴*/
	@GetMapping("delete/{id}")
	public String delete(@PathVariable(name="id") Long id) {
		userService.deleteById(id);	
		return "redirect:/list";	
	}
}
