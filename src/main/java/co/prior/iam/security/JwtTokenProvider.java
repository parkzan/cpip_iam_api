package co.prior.iam.security;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import co.prior.iam.module.user.model.response.UserRoleObject;
import co.prior.iam.module.user.service.UserRoleService;
import co.prior.iam.module.user.service.UserService;
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

	private final UserService userService;
	private final UserRoleService userRoleService;
	
	public JwtTokenProvider(UserService userService, UserRoleService userRoleService) {
		this.userService = userService;
		this.userRoleService = userRoleService;
	}
	
    public String generateToken(Authentication authentication, String jwtSecret, int jwtExpirationTime) {
    	log.info("Common generateToken");
    	
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Calendar jwtExpiredDateTime = Calendar.getInstance();
        jwtExpiredDateTime.add(Calendar.MINUTE, jwtExpirationTime / 60);

        List<UserRoleObject> payload = this.userRoleService.getUserRoleObject(userPrincipal.getUserId());
        return Jwts.builder()
                .setSubject(userPrincipal.getUserCode())
                .setIssuedAt(new Date())
                .setExpiration(jwtExpiredDateTime.getTime())
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .claim("roles", payload)
                .compact();
    }
    
    public long getUserIdFromJWT(String token, String jwtSecret) {
    	log.info("Common getUserIdFromJWT token: {}", token);
    	
    	Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return this.userService.getUserId(claims.getSubject());
    }

    public boolean validateToken(String token, String jwtSecret) {
    	log.info("Common validateToken token: {}", token);
    	
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
            return true;
            
        } catch (SignatureException ex) {
        	log.error("Invalid JWT signature");
//        	throw new ForbiddenException(ErrorCode.INVALID_JWT_SIGNATURE);
        	
        } catch (MalformedJwtException ex) {
        	log.error("Invalid JWT token");
//        	throw new ForbiddenException(ErrorCode.INVALID_JWT_TOKEN);
        	
        } catch (ExpiredJwtException ex) {
        	log.error("Expired JWT token");
//        	throw new ForbiddenException(ErrorCode.EXPIRED_JWT_TOKEN);
        	
        } catch (UnsupportedJwtException ex) {
        	log.error("Unsupported JWT token");
//        	throw new ForbiddenException(ErrorCode.UNSUPPORTED_JWT_TOKEN);
        	
        } catch (IllegalArgumentException ex) {
        	log.error("JWT claims string is empty.");
//        	throw new ForbiddenException(ErrorCode.JWT_CLAIM_EMPTY);
        } catch (Exception ex) {
            log.error("JWT error exception.");
        }
        
        return false;
    }
    
}
