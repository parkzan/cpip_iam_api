package co.prior.iam.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsUser;

@Repository
public interface IamMsUserRepository extends JpaRepository<IamMsUser, Long> {
	
	Collection<IamMsUser> findByIsDeleted(String isDeleted);
	
	Optional<IamMsUser> findByUserIdAndIsDeleted(Long userId, String isDeleted);
	
    Optional<IamMsUser> findByUserCodeAndIsDeleted(String userCode, String isDeleted);

    Boolean existsByUserCodeAndDisableFlagAndIsDeleted(String userCode, String disableFlag, String isDeleted);
}
