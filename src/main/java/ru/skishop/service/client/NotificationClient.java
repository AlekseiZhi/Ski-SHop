package ru.skishop.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skishop.dto.MailInfoDto;

@FeignClient(name = "notification", url = "${notification.url}")
public interface NotificationClient {

    @PostMapping("/mail/send")
    ResponseEntity<Void> senEmail(@RequestBody MailInfoDto mailInfoDto);
}