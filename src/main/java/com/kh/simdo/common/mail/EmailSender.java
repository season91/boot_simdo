package com.kh.simdo.common.mail;


import com.kh.simdo.common.code.ConfigCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@EnableAsync
public class EmailSender {

    // 1. 스프링으로부터 MailSender 의존성 주입 받기 (안전한 라이브러리니까 바로 주입)
    // 2. 메일 보내기위해 MimeMessage 구현해야하는데 이안에 또 4가지를 구현해야한다.

    // 오토와이어드 해도되는데~ 혹시모르니까 생성자주입으로 변경
    private final JavaMailSender mail;

    public EmailSender(JavaMailSender mail) {
        this.mail = mail;
    }


    // 새로운 thread를 생성해 메서드를 비동기로 실행할 수 있도록 처리
    // 실행결과로 반환해야할 값이 있다면 Future타입 객체를 반환하고, Future 객체를 통해 실행 결과값을 가져올 수 있다.
    @Async //스프링이 알아서  스레드 만들어서 비동기 돌릴거임.
    public void send(String to, String subject, String htmlText) {

        MimeMessage msg = mail.createMimeMessage();
        try {
            msg.setFrom(ConfigCode.EMAIL.desc);
            msg.setRecipients(Message.RecipientType.TO, to); //받는사람
            msg.setSubject(subject);
            msg.setContent(htmlText, "text/html; charset=UTF-8");
            mail.send(msg);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
