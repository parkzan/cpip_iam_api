package co.prior.iam.repository;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObjectRepository extends JpaRepository<IamMsObject,Long>  {
            Optional<IamMsObject> findByIamMsSystemAndObjectCodeAndIsDeleted(IamMsSystem iamMsSystem,String objectCode , String isDelete);

            Optional<IamMsObject> findByObjectId(Long objectId);

             Optional<IamMsObject> findByObjectIdAndIsDeleted(Long objectId ,String isDelete);
             Optional<IamMsObject> findByIamMsSystem_SystemIdAndObjectCodeAndIsDeleted(long systemId,String objectCode , String isDelete);


           List<IamMsObject> findByIamMsSystem_SystemIdAndIsDeleted(long systemId,String isDelete);

    List<IamMsObject> findByIamMsSystem_SystemIdAndObjectParent_ObjectIdAndIsDeleted(long systemId,long objectId,String isDelete);






}
