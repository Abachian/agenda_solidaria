package agenda_solidaria.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableAsync
public class AgendaSolidariaWebSecurityConfig {


	private static Logger logger = LoggerFactory.getLogger(AgendaSolidariaWebSecurityConfig.class);


	@Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
	    	.csrf((config) -> config.disable())
	    	.addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
	    	.authorizeHttpRequests((authorize) -> authorize
	    			.requestMatchers("/").permitAll()		// health check
	        		.requestMatchers("/version").permitAll()		// version
	    	        .requestMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs**/**").permitAll()
	    	        .requestMatchers("api/security/login").permitAll()
//	    	        .requestMatchers("/security/otp/*").permitAll()
	    	        .requestMatchers("/security/logout").authenticated()
	    	        .requestMatchers("/security/user-info/*", "/security/change-password", "/security/forget-password/*").permitAll()
					.requestMatchers("api/users/**").permitAll()
							.anyRequest().authenticated()
	    		);
	    return http.build();
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

//	@Bean
//	WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//					.allowedOrigins("*")
//					.exposedHeaders("x-file-name");
//			}
//		};
//	}

	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
