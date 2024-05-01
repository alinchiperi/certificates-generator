package ro.usv.certificates_generator.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.usv.certificates_generator.dto.AdeverintaAprobataDto;
import ro.usv.certificates_generator.model.CerereStatus;
import ro.usv.certificates_generator.model.Secretara;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;


    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachmentData, String attachmentName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            helper.addAttachment(attachmentName, new ByteArrayResource(attachmentData));

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error while sending email", e);
        }
    }

    public void sendEmailWithMessage(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error("Error while sending email", e);
        }
    }
}


