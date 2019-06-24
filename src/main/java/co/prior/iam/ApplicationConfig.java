package co.prior.iam;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.prior.iam.security.JwtAuthenticationFilter;
import co.prior.iam.security.UserPrincipal;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ApplicationConfig {

	@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditAwareImpl();
	}
	
	private class AuditAwareImpl implements AuditorAware<String> {
		
	    @Override
	    public Optional<String> getCurrentAuditor() {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
	            return Optional.of("ADMIN");
	        }

	        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
	        
	        return Optional.of(userPrincipal.getUserCode());
	    }
	}
	
}
