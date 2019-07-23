package co.prior.iam.module.object.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObjectInqueryService {

	private final ObjectRepository objectRepository;

	public ObjectInqueryService(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;
	}

	@Transactional
	public List<ObjectRespone> inqueryObject(long systemId) {
		log.info("Service inqueryObject systemId: {}", systemId);

		List<IamMsObject> listModel = this.objectRepository.findByIamMsSystem_SystemIdAndIsDeletedOrderByObjectId(systemId, AnswerFlag.N.toString());
		List<ObjectRespone> list = new ArrayList<>();
		if (!listModel.isEmpty()) {
			for (IamMsObject object : listModel) {
				if (object.getObjectParent() == null) {
					long count = 0 ;
					ObjectRespone respone = ObjectRespone.builder()
							.systemId(object.getIamMsSystem().getSystemId())
							.objectId(object.getObjectId())
							.objectCode(object.getObjectCode())
							.objectName(object.getObjectName())
							.objectTotalChild(countObjectChild(object , listModel ,count))
							.build();
					list.add(respone);
				}
			}
			
			return list;
		}
		
		throw new DataNotFoundException(ErrorCode.OBJECT_NOT_FOUND);
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
