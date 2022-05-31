package utils;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skishop.dto.UserInfoToken;
import ru.skishop.entity.CurrentUser;

import java.util.List;

public class SecurityMockUtils {

    public static Answer<Void> replaceTokenProcess() {
        return invocation -> {
            UserInfoToken userInfo = new UserInfoToken(1L, "test@test.ru", List.of("admin"));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userInfo.getId(),
                    userInfo.getEmail(),
                    List.of(new SimpleGrantedAuthority("ROLE_admin"))
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return null;
        };
    }

    public static void mockCurrentUser(CurrentUser currentUser) {
        Mockito.when(currentUser.getId())
                .thenReturn(1L);

        Mockito.when(currentUser.getEmail())
                .thenReturn("test@test.ru");

        Mockito.when(currentUser.getRoles())
                .thenReturn(List.of("admin"));
    }

}