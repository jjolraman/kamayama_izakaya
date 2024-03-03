package com.izakaya.website.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.izakaya.website.entity.ReserveEntity;
import com.izakaya.website.model.Reserve;
import com.izakaya.website.service.ReserveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ReservationController {
	
	private final ReserveService reserveService;
	
	@GetMapping("reserve")
	public String reserveForm(@ModelAttribute Reserve reserve) {
		List<Reserve> reserveList =  reserveService.findReserve();
		for(Reserve r : reserveList) {
	        System.out.println(r.getTime()); // 각 Reserve 객체의 time 값을 출력
	    }
		return "reserve";
	}
	
	@PostMapping("reserve")
	public String reserve(@ModelAttribute Reserve reserve) {
		reserveService.create(reserve);
		return "redirect:/";
	}
	
	@GetMapping("reserved/update")
	public String updateForm() {
		return "reserved/update";
	}

}