package ru.skishop.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.skishop.dto.UserInfoToken;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Data
@Component
@RequestScope
public class CurrentUser {

    private Long id;
    private String email;
    private List<String> roles;

    public void initialize(UserInfoToken userInfoToken) {
        this.id = userInfoToken.getId();
        this.email = userInfoToken.getEmail();
        this.roles = userInfoToken.getRoles();
    }


    //оставил для проверки, что происходит с состоянием бина
    @PostConstruct
    public void create() {
        System.out.println("CREATEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE " + id);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("DESTROYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY " + id);
    }
}