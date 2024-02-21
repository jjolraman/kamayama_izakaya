package com.example.practice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.practice.entity.PostEntity;
import com.example.practice.model.Post;
import com.example.practice.model.User;
import com.example.practice.service.PostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {
	
	private final PostService postService;
	
	@GetMapping("create")
	public String post(@ModelAttribute User user, Model model) {
		model.addAttribute("loginUser", user);
		return "post/create";
	}
	
	@PostMapping("create")
	public String postCreate(@ModelAttribute Post post,
							 @ModelAttribute User user) {
		postService.create(post, user);
		return "redirect:/post/list";
	}
	
	@GetMapping("list")
	public String findAll(Model model) {
		List<Post> postList = postService.findAll();
		model.addAttribute("posts", postList);
		return "post/list";
	} 
}
