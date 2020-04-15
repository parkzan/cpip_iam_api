package co.prior.iam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsUserRole;

@Repository
public interface IamMsUserRoleRepository extends JpaRepository<IamMsUserRole, Long> {

	Optional<IamMsUserRole> findByUserRoleIdAndIsDeleted(long userRoleId, String isDeleted);
	
	List<IamMsUserRole> findByIamMsUser_UserIdAndIsDeleted(long userId, String isDeleted);

	List<IamMsUserRole> findByIamMsSystem_SystemIdAndIsDeleted(long systemId, String isDeleted);
	List<IamMsUserRole> findByIamMsSystem_SystemIdAndIamMsUser_UserIdAndIsDeleted(long systemId,long userId ,String isDeleted);

	List<IamMsUserRole> findByIamMsRole_RoleIdAndIsDeleted(long roleId, String isDeleted);


	@Query(value = " select system_id from {h-schema}iam_ms_user_role where user_id = ?1  and is_deleted = 'N' group by  system_id" , nativeQuery = true )
	List<Long> getListSystemId(long user_id);

	boolean existsByIamMsSystem_SystemIdAndIamMsUser_UserIdAndIamMsRole_RoleIdAndIsDeleted(
			long systemId, long userId, long roleId, String isDeleted);

	List<IamMsUserRole> findByIamMsSystem_SystemCodeAndIsDeleted(String systemCode, String isDeleted);
}
