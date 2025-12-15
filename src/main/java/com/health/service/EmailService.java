package com.health.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 한글 발신자 이름 지원
     * @param to 수신자 이메일
     * @param subject 제목
     * @param text 내용 (HTML 가능)
     */
    public void sendEmail(String to, String subject, String text) throws Exception{
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 한글 발신자 이름
            helper.setFrom("ghddnjstlr@gmail.com", "MyCondition");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // HTML 형식 가능

            mailSender.send(message);
            System.out.println("메일 전송 완료: " + to);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("메일 전송 실패: " + e.getMessage());
            throw e;
        }
    }
    
}