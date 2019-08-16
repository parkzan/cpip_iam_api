package co.prior.iam.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsUser;

@Repository
public interface IamMsUserRepository extends PagingAndSortingRepository<IamMsUser, Long> {
	
	Page<IamMsUser> findPageableByIsIamAdminAndIsDeletedOrderByUserCode(
			String isIamAdmin, String isDeleted, Pageable pageable);
	
	Optional<IamMsUser> findByUserIdAndIsDeleted(long userId, String isDeleted);
	
    Optional<IamMsUser> findByUserCodeAndIsDeleted(String userCode, String isDeleted);

    Optional<IamMsUser> findByUserCodeAndIsIamAdminAndIsDeleted(String userCode, String isIamAdmin, String isDeleted);
    
    Optional<IamMsUser> findByIamMsSystem_SystemIdAndUserCodeAndIsDeleted(long systemId, String userCode, String isDeleted);



    boolean existsByUserCodeAndIsDeleted(String userCode, String isDeleted);
}
