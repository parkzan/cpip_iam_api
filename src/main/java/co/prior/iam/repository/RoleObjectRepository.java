package co.prior.iam.repository;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleObjectRepository  extends JpaRepository<IamMsRoleObject,Long> {

            Optional<IamMsRoleObject> findBySystemIdAndRoleIdAndObjectIdAndIsDeleted(Long systemId, Long roleId , Long objectId , String isDelete);

            List<IamMsRoleObject> findByRoleIdAndIsDeleted(Long roleId,String isDelete);

            Optional<IamMsRoleObject> findByRoleIdAndObjectId(Long roleId,Long objectId);
}
