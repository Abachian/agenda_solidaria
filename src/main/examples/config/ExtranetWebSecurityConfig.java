package ar.com.vwa.extranet.config;

import java.security.cert.CertificateException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
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

import ar.com.vwa.extranet.services.security.CompositeAuthenticationProvider;
import ar.com.vwa.extranet.services.security.JwtAuthorizationFilter;
import ar.com.vwa.extranet.services.security.LdapService;
import ar.com.vwa.extranet.services.security.SecurityConstants;
import ar.com.vwa.extranet.services.security.dummy.DummyAuthenticationProvider;
import ar.com.vwa.extranet.services.security.ldap.ActiveDirectoryLdapService;
import ar.com.vwa.extranet.services.security.ldap.LdapAuthenticationProvider;
import ar.com.vwa.extranet.services.security.ldap.LdapAvailable;

@Configuration
@EnableWebSecurity
@EnableAsync
public class ExtranetWebSecurityConfig {

	private static final String LDAP_TIMEOUT = "10000"; // 10 seg

	private static Logger logger = LoggerFactory.getLogger(ExtranetWebSecurityConfig.class);

	@Autowired(required = false)
	private LdapConfig ldapConfig;

	@Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
	    	.csrf((config) -> config.disable())
	    	.addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
	    	.authorizeHttpRequests((authorize) -> authorize
	    			.requestMatchers("/").permitAll()		// health check
	        		.requestMatchers("/version").permitAll()		// version
	    	        .requestMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs**/**").permitAll()
	    	        .requestMatchers("/security/login").permitAll()
	    	        .requestMatchers("/security/otp/*").permitAll()
	    	        .requestMatchers("/security/logout").authenticated()
	    	        .requestMatchers("/security/user-info/*", "/security/change-password", "/security/forget-password/*").permitAll()
	    	        .requestMatchers("/files/download/**").permitAll()
	    	        .requestMatchers("/**").authenticated()
	    		);
	    return http.build();
    }
	
	@Bean("ldapAuthProvider")
	@Profile(value = Environments.LOCAL)
	AuthenticationProvider dummyAuthProvider() throws Exception {
		return new DummyAuthenticationProvider();
	}

	@Bean("ldapAuthProvider")
	@Profile(value = Environments.CLOUD)
	AuthenticationProvider ldapAuthProvider() throws Exception {
		CompositeAuthenticationProvider provider = new CompositeAuthenticationProvider(
				new LdapAuthenticationProvider(LdapAvailable.NEW, ldapNuevoConfig(), SecurityConstants.LDAP_GROUPS),
				new LdapAuthenticationProvider(LdapAvailable.OLD, ldapActualConfig(), SecurityConstants.LDAP_GROUPS));

		return provider;

	}
	
	@Bean
	@Profile(value = Environments.CLOUD)
	LdapService activeDirectoryLdapService() throws Exception {
		ActiveDirectoryLdapService ldapService = new ActiveDirectoryLdapService(this.ldapNuevoConfig(),
				this.ldapActualConfig());
		return ldapService;

	}

	@Profile(value = Environments.CLOUD)
	public LdapTemplate ldapActualConfig() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(this.ldapConfig.getActualUrl());
		contextSource.setBase(this.ldapConfig.getActualBaseDn());
		contextSource.setUserDn(this.ldapConfig.getActualUsername());
		contextSource.setPassword(this.ldapConfig.getActualPassword());

		this.afterPropertiesLdapContext(contextSource);
		return new LdapTemplate(contextSource);
	}

	@Profile(value = Environments.CLOUD)
	public LdapTemplate ldapNuevoConfig() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(this.ldapConfig.getNuevoUrl());
		contextSource.setBase(this.ldapConfig.getNuevoBaseDn());
		contextSource.setUserDn(this.ldapConfig.getNuevoUsername());
		contextSource.setPassword(this.ldapConfig.getNuevoPassword());

		this.afterPropertiesLdapContext(contextSource);
		return new LdapTemplate(contextSource);
	}

	private void afterPropertiesLdapContext(LdapContextSource contextSource) {
		contextSource.setAnonymousReadOnly(false);

		Map<String, Object> environment = new HashMap<>();
		environment.put("com.sun.jndi.ldap.connect.timeout", LDAP_TIMEOUT);
		contextSource.setBaseEnvironmentProperties(environment);

		contextSource.afterPropertiesSet();

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

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("*")
					.exposedHeaders("x-file-name");
			}
		};
	}

	@EventListener(ApplicationReadyEvent.class)
	public void setup() {
		logger.info("Setting TimeZone UTC-3");
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("America/Argentina/Buenos_Aires")));
		
		logger.info("Setting TrustManager");
		TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
				
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}
				
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}
			}
        };
        
        try {
	        // Instalar el trust manager
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	
	        // Instalar un host verifier que acepte todos los hostnames
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	        
        }catch(Exception e) {
        	logger.error("ERROR al crear el trust manager", e);
        }
        
        // setteo para cxf
        System.setProperty("org.apache.cxf.stax.maxChildElements", "150000");
        System.setProperty("org.apache.cxf.stax.maxElementDepth", "150000");
        System.setProperty("org.apache.cxf.stax.maxAttributeCount", "150000");
        System.setProperty("org.apache.cxf.stax.maxAttributeSize", "150000");
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
