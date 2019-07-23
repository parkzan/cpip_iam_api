package co.prior.iam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;

@Repository
public interface ObjectRepository extends JpaRepository<IamMsObject, Long> {
	Optional<IamMsObject> findByIamMsSystemAndObjectCodeAndIsDeleted(IamMsSystem iamMsSystem, String objectCode, String isDelete);

	Optional<IamMsObject> findByObjectId(long objectId);

	Optional<IamMsObject> findByObjectIdAndIsDeleted(long objectId, String isDelete);

	Optional<IamMsObject> findByIamMsSystem_SystemIdAndObjectCodeAndIsDeleted(long systemId, String objectCode, String isDelete);

	List<IamMsObject> findByIamMsSystem_SystemIdAndIsDeletedOrderByObjectId(long systemId, String isDelete);

	List<IamMsObject> findByIamMsSystem_SystemIdAndObjectParent_ObjectIdAndIsDeleted(long systemId, long objectId, String isDelete);

	List<IamMsObject> findByObjectParent_ObjectIdAndIsDeleted(long objectId, String isDelete);

	Optional<IamMsObject> findByIamMsSystem_SystemIdAndObjectIdAndIsDeleted(long systemId, long objectId, String isDelete);

	Optional<IamMsObject> findByIamMsSystem_SystemCodeAndObjectParentNullAndIsDeleted(String SystemId ,String isDeleted);
}
