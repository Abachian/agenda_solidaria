package agenda_solidaria.security;

import agenda_solidaria.model.Role;
import agenda_solidaria.model.User;
import agenda_solidaria.service.exceptions.ServiceException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    @Qualifier("dbAuthProvider")
    private AuthenticationProvider dbAuthenticationProvider;

    private static Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);


    private long sessionExpirationTime= 2000000;

    @Override
    public AppUser login(User user, String password) {
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(user, password);
            auth = this.dbAuthenticationProvider.authenticate(auth);

            AppUser appUser = (AppUser) auth.getPrincipal();
            return appUser;
        } catch (AuthenticationException e) {
            logger.error("Error al autenticar " + user.getUsername(), e);
            throw ServiceException.unauthorizedError("Credenciales Invalidas");
        }
    }
    @Override
    public void logout(String sessionId) {
        SecurityContextHolder.clearContext();
    }

    @Override
    public AppUser getLoggedUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public List<String> getAuthorities() {
        return getLoggedUser().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }


    @Override
    public String generateJwtToken(User usuario, List<Role> permisos) {
        String token = Jwts.builder()
                .setSubject(usuario.getUsername())
                .claim("name", usuario.getUsername())
                .claim("email", usuario.getEmail())
                .claim("firstName", usuario.getFirstName())
                .claim("lastName", usuario.getLastName())
                .claim("permisos", Permission.build(permisos))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + sessionExpirationTime))
                .signWith(SignatureAlgorithm.RS512, KeyStore.getPrivateKey())
                .compact();

        return SecurityConstants.BEARER + token;
    }
} 