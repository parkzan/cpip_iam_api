package co.prior.iam.repository;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleObjectRepository  extends JpaRepository<IamMsRoleObject,Long> {

            Optional<IamMsRoleObject> findByIamMsSystemAndIamMsRoleAndIamMsObjectAndIsDeleted(IamMsSystem iamMsSystem, IamMsRole iamMsRole , IamMsObject iamMsObject , String isDelete);

            List<IamMsRoleObject> findByIamMsRoleAndIsDeleted(IamMsRole iamMsRole,String isDelete);

            Optional<IamMsRoleObject> findByIamMsRoleAndIamMsObject(IamMsRole iamMsRole,IamMsObject iamMsObject);

            Optional<IamMsRoleObject> findByIamMsObjectAndIsDeleted(IamMsObject iamMsObject , String isDelete);


}
