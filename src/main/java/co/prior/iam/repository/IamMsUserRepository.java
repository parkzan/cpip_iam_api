package co.prior.iam.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsUser;

@Repository
public interface IamMsUserRepository extends PagingAndSortingRepository<IamMsUser, Long> {
	
	Page<IamMsUser> findPageableByIamMsSystem_SystemIdAndIsDeleted(long systemId, String isDeleted, Pageable pageable);
	
	Optional<IamMsUser> findByUserIdAndIsDeleted(long userId, String isDeleted);

	
    Optional<IamMsUser> findByUserCodeAndIsDeleted(String userCode, String isDeleted);
    
    Optional<IamMsUser> findByUserIdAndUserPasswordAndIsDeleted(long userId, String userPassword, String isDeleted);
    
    Optional<IamMsUser> findByIamMsSystem_SystemIdAndUserCodeAndUserPasswordAndIsDeleted(
    		long systemId, String userCode, String userPassword, String isDeleted);

    Boolean existsByIamMsSystem_SystemIdAndUserCodeAndIsDeleted(long systemId, String userCode, String isDeleted);
}
