package co.prior.iam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsRoleObject;

@Repository
public interface RoleObjectRepository extends JpaRepository<IamMsRoleObject, Long> {

	List<IamMsRoleObject> findByIamMsRole_RoleIdAndIsDeleted(long roleId, String isDelete);

	List<IamMsRoleObject> findByIamMsRole_RoleIdAndIsDeletedAndIamMsObject_IsDeleted(long roleId, String isDelete, String isDeleteObj);

	List<IamMsRoleObject> findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIsDeleted(long systemId, long roleId, String isDelete);

	Optional<IamMsRoleObject> findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIamMsObject_ObjectIdAndIsDeleted(
			long systemId, long roleId, long objectId, String isDelete);
}
