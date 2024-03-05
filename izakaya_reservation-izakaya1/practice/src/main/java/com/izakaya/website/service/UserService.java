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
        String verificationToken = generateVerificationToken();
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
//                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .tel(user.getTel())
                .verificationToken(verificationToken)
                .enabled(false)
                .build();
        userRepository.save(userEntity);
        sendVerificationEmail(user.getEmail(), verificationToken);
    }

//    public User login(String username, String password) {
//        Optional<UserEntity> optionalUser = userRepository.findByEmail(user.getEmail());
//        if (optionalUser.isPresent()) {
//            UserEntity userEntity = optionalUser.get();
//            if (userEntity.getPassword().equals(password)) {
//                return userEntity.toUser();
//            }
//        }
//        return null;
//    }
    
    public User login(User user) {
		Optional<UserEntity> byuserEmail = userRepository.findByEmail(user.getEmail());
		
		if(byuserEmail.isPresent()) {
			UserEntity userEntity = byuserEmail.get();
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
        for (UserEntity userEntity : userEntityList) {
            userList.add(userEntity.toUser());
        }
        return userList;
    }

//    public User findById(Long id) {
//        Optional<UserEntity> userEntity = userRepository.findById(id);
//        if (userEntity.isPresent()) {
//            return userEntity.get().toUser();
//        }
//        return null;
//    }
    
    public User findByEmail(String email){
		Optional<UserEntity> userEntity = userRepository.findByEmail(email);
		
		if(userEntity.isPresent()) {
			return userEntity.get().toUser();
		}
		return null;
	}

    @Transactional
    public void update(User user) {
        Optional<UserEntity> byId = userRepository.findById(user.getId());
        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            userEntity.update(user.getId(),
//                    user.getUsername(),
            		user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getEmail());
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
        return UUID.randomUUID().toString();
    }

    private void sendVerificationEmail(String to, String verificationToken) {
        String subject = "会員登録のお知らせ";
        String body = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n";
        body += "この度、イザカヤホームページにご登録いただきありがとうございます。\n\n";
        body += "会員登録を完了するために、以下のリンクをクリックしてください。\n\n";
        body += "http://127.0.0.1:9000/verify?token=" + verificationToken + "\n\n";
        body += "リンクの有効期限は24時間です。期限内にクリックしてください。\n\n";
        body += "※リンクが無効になった場合は、お手数ですが再度登録手続きをお願いいたします。\n\n";
        body += "お問い合わせがある場合は、下記の連絡先までご連絡ください。\n\n";
        body += "お問い合わせ先：support@izakaya.co.jp\n\n";
        body += "会員登録の完了をお待ちしております。ありがとうございました。\n\n";
        body += "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}