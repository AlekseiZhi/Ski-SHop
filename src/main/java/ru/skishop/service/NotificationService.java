package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.skishop.dto.MailInfoDto;
import ru.skishop.service.client.NotificationClient;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationClient notificationClient;

    @Async
    public void sendMail(String mailTo, String mailFrom, String subject, String message) {
        MailInfoDto mailInfoDto = new MailInfoDto();
        mailInfoDto.setMailTo(mailTo);
        mailInfoDto.setMailFrom(mailFrom);
        mailInfoDto.setSubject(subject);
        mailInfoDto.setMessage(message);

        notificationClient.senEmail(mailInfoDto);
    }
}