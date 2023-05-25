package com.application.piunivesp.service;

import com.application.piunivesp.exception.type.BusinessException;
import com.application.piunivesp.security.model.PasswordResetToken;
import com.application.piunivesp.util.MailTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableAutoConfiguration
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value(value = "${link.front}")
    public static final String FRONT = "http://192.168.0.103:4200/#/";

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String subject, String message, List<String> to, Boolean isHtml) {
        try {
            if (isHtml) {
                MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(message, true);
                mimeMessageHelper.setTo(to.toArray(new String[to.size()]));
                this.javaMailSender.send(mimeMessage);
            } else {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setSubject(subject);
                mailMessage.setText(message);
                mailMessage.setTo(to.toArray(new String[to.size()]));
                this.javaMailSender.send(mailMessage);
            }
        } catch (MessagingException | MailException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void resetPasswordEmail(PasswordResetToken passwordResetToken) {
        List<String> userEmail = new ArrayList<>();
        userEmail.add(passwordResetToken.getUser().getEmail());
        sendMail(MailTemplate.SUBJECT_RESET_PASSWORD, getEmailResetPassword(passwordResetToken), userEmail, true);
    }

    private String getEmailResetPassword(PasswordResetToken passwordResetToken) {
        return MailTemplate.TEMPLATE_RESET_PASSWORD.replace("[USERNAME]", passwordResetToken.getUser().getName()).replace("[LINK]", getUrlResetPassword(passwordResetToken));
    }

    private String getUrlResetPassword(PasswordResetToken passwordResetToken) {
        return FRONT + "reset-password/change-password/" + passwordResetToken.getToken();
    }

}
