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

import com.izakaya.website.entity.PostEntity;
import com.izakaya.website.model.Post;
import com.izakaya.website.model.User;
import com.izakaya.website.service.PostService;

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
	public String post() {
		return "post/create";
	}
	
	@PostMapping("create")
	public String postCreate(@ModelAttribute Post post,
							 @SessionAttribute(name="loginUser", required = false) User user) {
		if(user == null) {
			return "redirect:/";
		}
		post.setUser(user);
		postService.create(post);
		return "redirect:/post/list";
	}
	
	@GetMapping("/list")
	public String findAll(Model model) {
		List<Post> postList = postService.findAll();
		model.addAttribute("posts", postList);
		return "post/list";
	} 
	

	@GetMapping("{postId}")
	public String viewPost(@PathVariable(name = "postId") Long postId,
			Model model) {
		Post post = postService.findById(postId);
		model.addAttribute("post", post);
		return "post/view";
	}
	
	/*게시글 수정 폼 이동*/
	@GetMapping("update/{postId}")
	public String editForm(@SessionAttribute(name="loginUser", required = false) User loginUser,
		@PathVariable(name="postId") Long postId, Model model) {
		Post post = postService.findById(postId);
		model.addAttribute("post", post);
		return "post/update";
	}
	
	/*게시글 수정*/
	@PostMapping("update/{postId}")
	public String editPost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
						   @PathVariable(name="postId") Long postId, @ModelAttribute Post post) {	
		postService.editPost(post, postId);
		return "redirect:/post/{postId}";
	}
	
	/*게시글 삭제*/
	@GetMapping("delete/{postId}")
	public String remove(@SessionAttribute(name="loginUser", required = false) User loginUser,
						 @PathVariable(name = "postId") Long postId, Model model) {
		postService.removePost(postId, loginUser);
		return "redirect:/post/list";
	}
	
}
