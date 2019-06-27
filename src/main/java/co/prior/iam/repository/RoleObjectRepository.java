package co.prior.iam.repository;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleObjectRepository  extends JpaRepository<IamMsRoleObject,Long> {



            List<IamMsRoleObject> findByIamMsRoleAndIsDeleted(IamMsRole iamMsRole , String isDelete);

            List<IamMsRoleObject> findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIsDeleted(long systemId,long roleId,String isDelete);
            Optional<IamMsRoleObject> findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIamMsObject_ObjectIdAndIsDeleted(long systemId , long roleId , long objectId , String isDelete );


}
