package com.example.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.practice.model.Reserve;
import com.example.practice.service.ReserveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class reservationController {
	
	private final ReserveService reserveService;
	
	@GetMapping("reserve")
	public String reserveForm() {
		return "reserve";
	}
	
	@PostMapping("reserve")
	public String reserve(@ModelAttribute Reserve reserve) {
		reserveService.create(reserve);
		return "redirect:/";
	}
}
