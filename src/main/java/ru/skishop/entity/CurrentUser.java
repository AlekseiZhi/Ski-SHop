package ru.skishop.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.skishop.dto.UserInfoToken;

import java.util.List;

@Component
@RequestScope
@Data
public class CurrentUser {

    private Long id;
    private String email;
    private List<String> roles;

    public CurrentUser() {
//        this.id = 1L;
//        this.email = "test@eamail";
//        this.roles = List.of("admin");
    }

    public void initialize(UserInfoToken userInfoToken) {
        this.id = userInfoToken.getId();
        this.email = userInfoToken.getEmail();
        this.roles = userInfoToken.getRoles();
    }
}