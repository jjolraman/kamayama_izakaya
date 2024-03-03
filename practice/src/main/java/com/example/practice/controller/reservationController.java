package com.example.practice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.practice.model.Member;
import com.example.practice.model.Post;
import com.example.practice.model.Reserve;
import com.example.practice.model.User;
import com.example.practice.service.ReserveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class reservationController {
	
	private final ReserveService reserveService;
	
	/*예약 폼 이동*/
	@GetMapping("reserve")
	public String reserveForm(@SessionAttribute(name="loginUser", required = false) User loginUser,
			@RequestParam(name="date", required = false) String dateString,
			@ModelAttribute Reserve reserve, Model model) {
		/*dateString이 null이면 오늘날짜 넣음*/
		LocalDate date = (dateString == null || dateString.isEmpty()) ?
                LocalDate.now() :
                LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		model.addAttribute("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		List<Reserve> allReserve = reserveService.findAllReserve(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		model.addAttribute("reserve", allReserve);
		log.info("date: {}", date);
		log.info("allReserve: {}", allReserve);
		return "reserve";
	}
	
	/*예약하기*/
	@PostMapping("reserve")
	public String reserve(@SessionAttribute(name="loginUser", required = false) User loginUser,
		@ModelAttribute Reserve reserve) {
		reserve.setUser(loginUser);
		reserveService.create(reserve);
		return "redirect:/";
	}
	
	/*예약조회*/
	@GetMapping("reserved/list")
	public String findReserve(@SessionAttribute(name="loginUser", required = false) User loginUser,
			Model model) {
		List<Reserve> userReserves = reserveService.findReservesByUserId(loginUser.getId());
		model.addAttribute("reserves", userReserves);
		return "/reserved/list";
	}
	
	/*예약변경*/
	@GetMapping("reserved/update/{reserveId}")
	public String updateForm(@PathVariable(name="reserveId") Long reserveId) {
		reserveService.deleteReserve(reserveId);
		return "redirect:/reserve";
	}
		
	/*예약취소*/
	@GetMapping("reserved/delete/{reserveId}")
	public String deleteReserve(@PathVariable(name="reserveId") Long reserveId) {
		reserveService.deleteReserve(reserveId);
		return "redirect:/reserved/list";
	}

}
