package co.prior.iam.repository;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleObjectRepository  extends JpaRepository<IamMsRoleObject,Long> {



            List<IamMsRoleObject> findByIamMsRoleAndIsDeleted(IamMsRole iamMsRole , String isDelete);

            List<IamMsRoleObject> findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIsDeleted(long systemId,long roleId,String isDelete);


}
