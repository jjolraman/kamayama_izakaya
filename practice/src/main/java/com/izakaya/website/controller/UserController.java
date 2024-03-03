package com.izakaya.website.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.izakaya.website.model.User;
import com.izakaya.website.repository.UserRepository;
import com.izakaya.website.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {
	// 생성자 주입
	private final UserService userService;
	private final UserRepository userRepository;
	
	//메인을 index로 변경 main쓰면 원상복구
	@GetMapping()
	public String main() {
		return "index";
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
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("list")
	public String findAll(Model model) {
		List<User> UserList = userService.findAll();
		model.addAttribute("userList", UserList);
		return "user/list";
	}
	
	@GetMapping("list/{id}")
	public String findbyId(@PathVariable(name="id") Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "user/detail";
	}
	
	@GetMapping("update/{id}")
	public String updateForm(@PathVariable(name="id") Long id, Model model) {
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
	public String delete(@PathVariable(name="id") Long id) {
		userService.deleteById(id);	
		return "redirect:/list";	
	}
}
