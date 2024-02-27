package com.izakaya.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.izakaya.website.model.Reserve;
import com.izakaya.website.service.ReserveService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReservationController {
	
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
