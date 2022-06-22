package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.skishop.dto.MailInfoDto;
import ru.skishop.service.client.NotificationClient;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationClient notificationClient;
    private final RabbitTemplate rabbitTemplate;

    @Async
    public void sendMailFeign(String mailTo, String mailFrom, String subject, String message) {
        MailInfoDto mailInfoDto = new MailInfoDto();
        mailInfoDto.setMailTo(mailTo);
        mailInfoDto.setMailFrom(mailFrom);
        mailInfoDto.setSubject(subject);
        mailInfoDto.setMessage(message);

        notificationClient.senEmail(mailInfoDto);
    }

    @Async
    public void sendMailRabbit(String mailTo, String mailFrom, String subject, String message) {
        MailInfoDto mailInfoDto = new MailInfoDto();
        mailInfoDto.setMailTo(mailTo);
        mailInfoDto.setMailFrom(mailFrom);
        mailInfoDto.setSubject(subject);
        mailInfoDto.setMessage(message);

        rabbitTemplate.convertAndSend("notificationQueue", mailInfoDto);
    }
}