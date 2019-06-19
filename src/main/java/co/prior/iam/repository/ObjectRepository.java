package co.prior.iam.repository;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObjectRepository extends JpaRepository<IamMsObject,Long>  {
            Optional<IamMsObject> findBySystemIdAndObjectCodeAndIsDeleted(Long systemId,String objectCode , String isDelete);

            Optional<IamMsObject> findByObjectId(Long objectId);

            List<IamMsObject> findBySystemIdAndIsDeleted(Long systemId , String isDelete);

            List<IamMsObject> findByIsDeleted(String isDelete);

            Optional<IamMsSystem> findByIamMsSystem(IamMsSystem iamMsSystem);




}
