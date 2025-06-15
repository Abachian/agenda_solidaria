package agenda_solidaria.security;

import agenda_solidaria.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("dbAuthProvider")
public class DbAuthenticationProvider implements AuthenticationProvider {
    
    private static Logger logger = LoggerFactory.getLogger(DbAuthenticationProvider.class);
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = (User) authentication.getPrincipal();
        
        if(passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            AppUser appUser = new AppUser(user.getUsername(), Collections.emptyList());
            appUser.setEmail(user.getEmail());
            appUser.setFirstName(user.getFirstName());
            appUser.setLastName(user.getLastName());
            Authentication auth = new UsernamePasswordAuthenticationToken(user,
                    authentication.getCredentials().toString(), appUser.getAuthorities());
            return auth;
        } else {
            logger.error("Se presentaron credenciales invalidas");
            throw new BadCredentialsException("Credenciales invalidas");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
} 