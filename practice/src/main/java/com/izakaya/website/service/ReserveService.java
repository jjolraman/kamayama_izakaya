package com.izakaya.website.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izakaya.website.entity.ReserveEntity;
import com.izakaya.website.entity.UserEntity;
import com.izakaya.website.model.Reserve;
import com.izakaya.website.repository.ReserveRepository;
import com.izakaya.website.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.izakaya.website.model.Member; // Member 열거형 import 추가

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void create(Reserve reserve) {
        UserEntity userEntity = null;
        if (reserve.getUser() != null) {
            Optional<UserEntity> byId = userRepository.findById(reserve.getUser().getId());
            if (byId.isPresent()) {
                userEntity = byId.get(); 
            }
        }    

        ReserveEntity reserveEntity = ReserveEntity.builder()
            .id(reserve.getId())
            .user(userEntity)
            .person(reserve.getPerson())
            .menu(reserve.getMenu())
            .date(reserve.getDate())
            .time(reserve.getTime())
//            .member(userEntity != null ? Member.MEMBER : Member.NON_MEMBER) // 수정: 열거형 상수 수정
            .email(reserve.getEmail())
            .build();

        reserveRepository.save(reserveEntity);

        // 예약 정보가 생성되면 이메일을 발송합니다.
        if (userEntity != null) {
            sendReservationConfirmationEmail(userEntity.getEmail(), reserve);
        }
    }

    private void sendReservationConfirmationEmail(String userEmail, Reserve reserve) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("【かまやま】ご予約が確定しました。");
        // 예약 정보를 메일 본문에 추가.
        String mailContent = "ご予約内容\n"
                           + "■来店日時: " + reserve.getDate()
                           + " " + reserve.getTime() + "\n"
                           + "■コース: " + reserve.getMenu()
                           + "■人数: " + reserve.getPerson()+"名様" + "\n"
                           + " この度は、当店をご予約いただきありがとうございます。" + "\n"
                           + "スタッフ一同、お会いできますことを楽しみにお待ちしております。"  + "\n"
                           + "どうぞ、お気をつけてお越しください。" + "\n"
                           + "ー居酒屋かまやまー";
       
      
        message.setText(mailContent);
        javaMailSender.send(message);
    }
	/*날짜에 해당하는 모든예약조회*/
    public List<Reserve> findAllReserve(String date) { 
        List<ReserveEntity> byDate = reserveRepository.findByDate(date);
        List<Reserve> reserveList = new ArrayList<>();
        for(ReserveEntity reserveEntity : byDate) {
            reserveList.add(reserveEntity.toReserve());
        }
        return reserveList;
    }
    /*회원의 모든예약조회*/
    public List<Reserve> findReservesByUserId(Long userId) {
        List<ReserveEntity> reserveEntities = reserveRepository.findByUserId(userId);
        List<Reserve> reserves = new ArrayList<>();
        for (ReserveEntity reserveEntity : reserveEntities) {
            reserves.add(reserveEntity.toReserve());
        }
        return reserves;
    }
    /*회원의 예약취소(삭제)*/
    @Transactional
    public void deleteReserve(Long reserveId) {
        Optional<ReserveEntity> reserveById = reserveRepository.findById(reserveId);
        
        if(reserveById.isPresent()) {
            ReserveEntity reserveEntity = reserveById.get();
            reserveRepository.delete(reserveEntity);
        }
    }
    
    /*비회원의 예약조회*/
	public List<Reserve> findReservesByEmail(String email) {
	    List<ReserveEntity> reserveEntities = reserveRepository.findByEmail(email);
	    List<Reserve> reserves = new ArrayList<>();
	    for (ReserveEntity reserveEntity : reserveEntities) {
	        reserves.add(reserveEntity.toReserve());
	    }
	    return reserves;
	}
	
	/*비회원의 예약취소(삭제)*/
	@Transactional
	public void deleteReserves(String email) {
		List<ReserveEntity> byEmail = reserveRepository.findByEmail(email);
		reserveRepository.deleteAll(byEmail);
	}
}
