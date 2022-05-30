package utils;

import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skishop.entity.CurrentUser;

import java.util.List;

public class SecurityMockUtils {
    public static Answer<Void> replaceTokenProcess(CurrentUser currentUser) {
        return invocation -> {
            currentUser.setId(1L);
            currentUser.setEmail("test@email");
            currentUser.setRoles(List.of("admin"));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(1L, "", List.of(new SimpleGrantedAuthority("ROLE_admin")));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return null;
        };
    }
}