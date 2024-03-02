package com.izakaya.website.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.izakaya.website.entity.UserEntity;
import com.izakaya.website.model.User;
import com.izakaya.website.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void create(User user) {
        String verificationToken = generateVerificationToken(); // 인증 토큰 생성

        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .email(user.getEmail())
                .verificationToken(verificationToken)
                .enabled(false)
                .build();
        userRepository.save(userEntity);
        
        // 이메일 전송 코드 추가
        sendVerificationEmail(user.getEmail(), verificationToken);
    }

    public User login(User user) {
        Optional<UserEntity> byUserName = userRepository.findByUsername(user.getUsername());
        
        if(byUserName.isPresent()) {
            UserEntity userEntity = byUserName.get();
            if(userEntity.getPassword().equals(user.getPassword())) {
                return userEntity.toUser();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<User> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList) {
            userList.add(userEntity.toUser());
        }
        return userList;
    }

    public User findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        
        if(userEntity.isPresent()) {
            return userEntity.get().toUser();
        }
        return null;
    }
    
    @Transactional
    public void update(User user) {
        Optional<UserEntity> byId = userRepository.findById(user.getId());
        
        if(byId.isPresent()) {
            UserEntity userEntity = byId.get();
            userEntity.update(user.getId(),
                              user.getUsername(),
                              user.getPassword(), 
                              user.getName(), 
                              user.getEmail(), 
                              user.getBirth());
        }
    } 

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    
    @Transactional
    public boolean verifyEmail(String emailToken, RedirectAttributes redirectAttributes) {
        Optional<UserEntity> optionalUser = userRepository.findByVerificationToken(emailToken);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setEnabled(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString(); // 랜덤한 UUID를 생성하여 반환
    }

    private void sendVerificationEmail(String to, String verificationToken) {
        // 이메일 내용 설정
    	String subject = "회원가입 인증 메일";
    	String body = "회원가입을 완료하려면 아래 링크를 클릭하세요:\n";
    	body += "http://127.0.0.1:9000/verify?token=" + verificationToken;

        // 이메일 보내기
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
