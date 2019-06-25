package co.prior.iam.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsUser;

@Repository
public interface IamMsUserRepository extends PagingAndSortingRepository<IamMsUser, Long> {
	
	Page<IamMsUser> findByIsDeleted(String isDeleted, Pageable pageable);
	
	Optional<IamMsUser> findByUserIdAndIsDeleted(Long userId, String isDeleted);
	
    Optional<IamMsUser> findByUserCodeAndIsDeleted(String userCode, String isDeleted);
    
    Optional<IamMsUser> findByUserCodeAndUserPasswordAndIsDeleted(String userCode, String userPassword, String isDeleted);

    Boolean existsByUserCodeAndDisableFlagAndIsDeleted(String userCode, String disableFlag, String isDeleted);
}
