package co.prior.iam.module.object.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObjectChildInqueryService {

	private SystemRepository systemRepository;
	private ObjectRepository objectRepository;

	public ObjectChildInqueryService(SystemRepository systemRepository, ObjectRepository objectRepository) {

		this.systemRepository = systemRepository;
		this.objectRepository = objectRepository;

	}

	@Transactional
	public List<ObjectRespone> inqueryChildObject(Long systemId, Long objectId) {
		log.info("Service inqueryChildObject: {}", objectId);

		List<IamMsObject> listObject = objectRepository.findByIamMsSystem_SystemIdAndObjectParent_ObjectIdAndIsDeleted(
				systemId, objectId, AnswerFlag.N.toString());

		List<IamMsObject> listAllObject = objectRepository.findByIamMsSystem_SystemIdAndIsDeleted(systemId,
				AnswerFlag.N.toString());

		List<ObjectRespone> list = new ArrayList<>();

		if (listObject.isEmpty()) {
			throw new DataNotFoundException("data not found");
		}
		for (IamMsObject object : listObject) {


			long count = 0;
			ObjectRespone respone = new ObjectRespone();
			respone.setSystemId(object.getIamMsSystem().getSystemId());
			respone.setObjectCode(object.getObjectCode());
			respone.setObjectId(object.getObjectId());
			respone.setObjectParentId(object.getObjectParent().getObjectId());
			respone.setObjectTotalChild(countObjectChild(object, listAllObject, count));

			list.add(respone);

		}
		return list;

	}

	private long countObjectChild(IamMsObject root , List<IamMsObject> list  , long count ) {
		for (IamMsObject obj : list) {
			if(obj.getObjectParent() != null && obj.getObjectParent().getObjectId().equals(root.getObjectId())) {
				count++ ;
				countObjectChild(obj, list ,count );
			}
		}
		return count;
	}
}
