package co.prior.iam.module.user.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.repository.IamMsUserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	private final IamMsUserRepository iamMsUserRepository;
	
	public UserService(IamMsUserRepository iamMsUserRepository) {
		this.iamMsUserRepository = iamMsUserRepository;
	}
	
	public Collection<IamMsUser> getUsers() {
		log.info("Service getUsers");
		
		return this.iamMsUserRepository.findByIsDeleted("N");
	}
	
	public IamMsUser getUser(long userId) throws Exception {
		log.info("Service getUser userId: {}", userId);
		
		return this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, "N")
				.orElseThrow(() -> new Exception("user not found"));
	}
	
}
