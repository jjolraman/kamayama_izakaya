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
    
    @PostMapping("reserve")
    public String reserve(@SessionAttribute(name="loginUser", required = false) User loginUser,
            @ModelAttribute Reserve reserve, HttpServletRequest request) {
        if(loginUser != null) {
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

    private void sendReservationEmail(String emailAddress, Reserve reserve) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("your-email@example.com");
            helper.setTo(emailAddress);
            helper.setSubject("Reservation Confirmation");

            // 이메일 본문에 예약 정보를 추가
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Your reservation details:\n");
            emailContent.append("Person: ").append(reserve.getPerson()).append("\n");
            emailContent.append("Menu: ").append(reserve.getMenu()).append("\n");
            emailContent.append("Date: ").append(reserve.getDate()).append("\n");
            emailContent.append("Time: ").append(reserve.getTime()).append("\n");
            helper.setText(emailContent.toString());

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email for reservation confirmation: {}", e.getMessage());
        }
    }
    
    @GetMapping("reserved/list")
    public String findReserve(@SessionAttribute(name="loginUser", required = false) User loginUser,
            Model model) {
        List<Reserve> userReserves = reserveService.findReservesByUserId(loginUser.getId());
        model.addAttribute("reserves", userReserves);
        return "/reserved/list";
    }
    
    @GetMapping("reserved/nonList")
    public String findReserve(Model model) {
        return "/reserved/nonList";
    }
    
    @GetMapping("reserved/update/{reserveId}")
    public String updateForm(@PathVariable(name="reserveId") Long reserveId) {
        reserveService.deleteReserve(reserveId);
        return "redirect:/reserve";
    }
        
    @GetMapping("reserved/delete/{reserveId}")
    public String deleteReserve(@PathVariable(name="reserveId") Long reserveId) {
        reserveService.deleteReserve(reserveId);
        return "redirect:/reserved/list";
    }
}
