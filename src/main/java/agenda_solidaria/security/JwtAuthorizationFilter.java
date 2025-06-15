package agenda_solidaria.security;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private static Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (existsJWT(request, response)) {
				Claims claims = validateToken(request);
				if (claims.getSubject() != null) {
					String username = claims.getSubject();

					List<GrantedAuthority> collect = Collections.emptyList();
					
					AppUser user = new AppUser(username, collect);
					user.setFirstName(claims.get("firstName", String.class));
					user.setLastName(claims.get("lastName", String.class));
					user.setEmail(claims.get("email", String.class));

					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, collect);
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
					SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			logger.error("Error al validar el token", e);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(SecurityConstants.HEADER_NAME).replace(SecurityConstants.BEARER, "");
		return Jwts.parser().setSigningKey(KeyStore.getPublicKey()).parseClaimsJws(jwtToken).getBody();
	}

	private boolean existsJWT(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(SecurityConstants.HEADER_NAME);
		if (authenticationHeader == null || !authenticationHeader.startsWith(SecurityConstants.BEARER))
			return false;
		return true;
	}
}
