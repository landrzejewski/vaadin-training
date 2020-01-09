package pl.training.shop.users.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    public boolean authenticate(String login, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
        try {
            logout();
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return true;
        } catch (Exception ex) {
            log.warning(ex.getMessage());
            return false;
        }
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

}
