package co.prior.iam.security;

import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication, String jwtSecret, int jwtExpirationTime) {
    	log.info("Common generateToken");
    	
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Calendar jwtExpiredDateTime = Calendar.getInstance();
        jwtExpiredDateTime.add(Calendar.MINUTE, jwtExpirationTime / 60);

        return Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getUserId()))
                .setIssuedAt(new Date())
                .setExpiration(jwtExpiredDateTime.getTime())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public Long getUserIdFromJWT(String token, String jwtSecret) {
    	log.info("Common getUserIdFromJWT token: {}", token);
    	
    	Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token, String jwtSecret) {
    	log.info("Common validateToken token: {}", token);
    	
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
            
        } catch (SignatureException ex) {
        	log.error("Invalid JWT signature");
        	
        } catch (MalformedJwtException ex) {
        	log.error("Invalid JWT token");
        	
        } catch (ExpiredJwtException ex) {
        	log.error("Expired JWT token");
        	
        } catch (UnsupportedJwtException ex) {
        	log.error("Unsupported JWT token");
        	
        } catch (IllegalArgumentException ex) {
        	log.error("JWT claims string is empty.");
        }
        
        return false;
    }
    
}
