package com.izakaya.website.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.izakaya.website.model.Reserve;
import com.izakaya.website.model.User;
import com.izakaya.website.service.ReserveService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.MimeMessageHelper;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReservationController {
    
    private final ReserveService reserveService;
    
    @Autowired
    private JavaMailSender javaMailSender;
    /*예약 폼 이동*/
    @GetMapping("reserve")
    public String reserveForm(@SessionAttribute(name="loginUser", required = false) User loginUser,
            @RequestParam(name="date", required = false) String dateString,
            @ModelAttribute Reserve reserve, Model model) {
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
    //예약 - 회원 비회원 나눠짐
    @PostMapping("reserve")
    public String reserve(@SessionAttribute(name="loginUser", required = false) User loginUser,
            @ModelAttribute Reserve reserve, HttpServletRequest request) {
        // 필수 정보가 모두 입력되었는지 확인
        if (reserve.getMenu() == null || reserve.getDate() == null || reserve.getTime() == null || reserve.getPerson() == 0) {
            // 필수 정보 중 하나라도 누락된 경우 예약을 처리하지 않고 예약 페이지로 이동
            return "redirect:/reserve"; 
        }
        
        // 로그인한 사용자인 경우
        if (loginUser != null) {
            reserve.setUser(loginUser);
        } else {
            // 비로그인 사용자의 경우 이메일 주소를 폼에서 받아옴
            String email = reserve.getEmail(); // getEmail()로 이메일 주소 가져오도록 수정
            reserve.setEmail(email);
        }
        
        // 예약 저장
        reserveService.create(reserve);
        
        // 이메일 발송
        if (loginUser == null) {
            sendReservationEmail(reserve.getEmail(), reserve); // 예약 정보와 이메일 주소를 이용하여 예약 확인 이메일 발송
        }
        
        return "redirect:/";
    }

    //비회원 예약
    private void sendReservationEmail(String emailAddress, Reserve reserve) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("your-email@example.com");
            helper.setTo(emailAddress);
            helper.setSubject("【かまやま】ご予約が確定しました。");

            // 이메일 본문에 예약 정보를 추가
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("ご予約内容\n");
            emailContent.append("■来店日時: ").append(reserve.getDate()).append(" ").append(reserve.getTime()).append("\n");
            emailContent.append("■コース: ").append(reserve.getMenu()).append("\n");
            emailContent.append("■人数: ").append(reserve.getPerson()).append("名様\n");
            emailContent.append("この度は、当店をご予約いただきありがとうございます。\n");
            emailContent.append("スタッフ一同、お会いできますことを楽しみにお待ちしております。\n");
            emailContent.append("どうぞ、お気をつけてお越しください。\n");
            emailContent.append("ー居酒屋かまやまー\n");
            helper.setText(emailContent.toString());

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("이메일 송신 실패: {}", e.getMessage());
        }
    }
    /*예약조회*/
    @GetMapping("reserved/list")
    public String findReserve(@SessionAttribute(name="loginUser", required = false) User loginUser,
            Model model) {
        List<Reserve> userReserves = reserveService.findReservesByUserId(loginUser.getId());
        model.addAttribute("reserves", userReserves);
        return "/reserved/list";
    }
    
    /*비회원 예약조회 이동*/
	@GetMapping("reserved/findEmail")
	public String findByEmail() {
		return "/reserved/findEmail";
	}
	
	/*비회원 예약조회*/
	@GetMapping("reserved/nonList")
	public String findReserve(@RequestParam(name="email", required = false) String email ,Model model) {
		List<Reserve> byEmail = reserveService.findReservesByEmail(email);
		model.addAttribute("reserves", byEmail);
		return "/reserved/nonList";
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
    
    /*비회원 예약취소*/
	@GetMapping("reserved/remove/{email}")
	public String removeReserve(@PathVariable(name="email") String email) {
		reserveService.deleteReserves(email);
		return "redirect:/";
	}
}
