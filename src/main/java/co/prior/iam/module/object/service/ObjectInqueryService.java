package co.prior.iam.module.object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.module.role.model.respone.ObjectModel;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ObjectInqueryService {

	ObjectRepository objectRepository;
	SystemRepository systemRepository;

	public ObjectInqueryService(ObjectRepository objectRepository, SystemRepository systemRepository) {

		this.objectRepository = objectRepository;
		this.systemRepository = systemRepository;

	}

	@Transactional
	public List<ObjectRespone> inqueryObject(Long systemId) throws Exception {
		log.info("Service inqueryObject: {}", systemId);

		List<IamMsObject> listModel = objectRepository.findByIamMsSystem_SystemIdAndIsDeleted(systemId,
				AnswerFlag.N.toString());
		List<ObjectRespone> list = new ArrayList<>();




		if (!listModel.isEmpty()) {

			for (IamMsObject object : listModel) {

				if (object.getObjectParent() == null) {

					long count = 0 ;

					ObjectRespone respone = new ObjectRespone();

					respone.setSystemId(object.getIamMsSystem().getSystemId());
					respone.setObjectCode(object.getObjectCode());
					respone.setObjectId(object.getObjectId());
					respone.setObjectTotalChild(countObjectChild(object , listModel ,count));

					list.add(respone);

				}
			}
			return list;

		}
		throw new DataNotFoundException("data not found");
	}

	private long countObjectChild(IamMsObject root , List<IamMsObject> list  , long count ) throws Exception{

			for (IamMsObject obj : list) {
				if(obj.getObjectParent() != null){
					if (obj.getObjectParent().getObjectId() == root.getObjectId()) {

						count++ ;
						countObjectChild(obj, list ,count );

					}

				}

			}
		return count;
		}



}
