package co.prior.iam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;

@Repository
public interface RoleRepository extends JpaRepository<IamMsRole, Long> {

	Optional<IamMsRole> findByRoleCodeAndIamMsSystemAndIsDeleted(String roleCode, IamMsSystem iamMsSystem, String isDelete);

	Optional<IamMsRole> findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(String roleCode, long systemId, String isDelete);


	List<IamMsRole> findByIamMsSystem_SystemIdAndIsDeletedOrderByRoleId(long systemId, String isDelete);

	Optional<IamMsRole> findByRoleIdAndIsDeleted(long roleId, String isDelete);


}
