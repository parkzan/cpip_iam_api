package co.prior.iam.security;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.repository.IamMsUserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomDetailsService implements UserDetailsService {
	
    private final IamMsUserRepository iamMsUserRepository;

    public CustomDetailsService(IamMsUserRepository iamMsUserRepository) {
    	this.iamMsUserRepository = iamMsUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userCode) {
    	log.info("Service loadUserByUsername userCode: {}", userCode);
    	
    	IamMsUser iamMsUser = this.iamMsUserRepository.findByUserCodeAndIsDeleted(userCode, "N")
    			.orElseThrow(() -> new UsernameNotFoundException("user not found with code: " + userCode));
    	
    	boolean isAccountNonLocked = iamMsUser.getNoOfFailTimes() >= 3? Boolean.FALSE : Boolean.TRUE;
    	boolean isEnabled = "Y".equalsIgnoreCase(iamMsUser.getFirstTimeLogin())
    			|| "Y".equalsIgnoreCase(iamMsUser.getDisableFlag())? Boolean.FALSE : Boolean.TRUE;
    	
        return UserPrincipal.builder()
        		.userId(iamMsUser.getUserId())
        		.userCode(iamMsUser.getUserCode())
        		.userPassword(iamMsUser.getUserPassword())
        		.localFirstName(iamMsUser.getLocalFirstName())
        		.localMiddleName(iamMsUser.getLocalMiddleName())
        		.localLastName(iamMsUser.getLocalLastName())
        		.engFirstName(iamMsUser.getEngFirstName())
        		.engMiddleName(iamMsUser.getEngMiddleName())
        		.engLastName(iamMsUser.getEngLastName())
        		.isAccountNonLocked(isAccountNonLocked)
        		.isEnabled(isEnabled)
        		.build();
    }
    
    public UserDetails loadUserById(Long userId) {
    	log.info("Service loadUserById userId: {}", userId);
    	
    	IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, "N")
        		.orElseThrow(() -> new UsernameNotFoundException("user not found with id: " + userId));

        return UserPrincipal.builder()
        		.userId(iamMsUser.getUserId())
        		.userCode(iamMsUser.getUserCode())
        		.userPassword(iamMsUser.getUserPassword())
        		.localFirstName(iamMsUser.getLocalFirstName())
        		.localMiddleName(iamMsUser.getLocalMiddleName())
        		.localLastName(iamMsUser.getLocalLastName())
        		.engFirstName(iamMsUser.getEngFirstName())
        		.engMiddleName(iamMsUser.getEngMiddleName())
        		.engLastName(iamMsUser.getEngLastName())
        		.authorities("Y".equals(iamMsUser.getIsIamAdmin())? Arrays.asList(new SimpleGrantedAuthority("IAM_ADMIN")) : null)
        		.build();
    }
    
}
