package co.prior.iam.module.object.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.object.model.request.ObjectDeleteReq;
import co.prior.iam.repository.ObjectRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObjectDeleteService {

	private final ObjectRepository objectRepository;

	public ObjectDeleteService(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;
	}

	@Transactional
	public void deleteObject(ObjectDeleteReq objectDeleteReq) {
		log.info("Service deleteObject: {}", objectDeleteReq);

		IamMsObject root = this.objectRepository.findByIamMsSystem_SystemIdAndObjectIdAndIsDeleted(
				objectDeleteReq.getSystemId(), objectDeleteReq.getObjectId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.OBJECT_NOT_FOUND));
		
		List<IamMsObject> listObject = new ArrayList<>();
		Stack<IamMsObject> stack = new Stack<>();
		listObject.add(root);
		stack.push(root);
		
		this.addChild(root, listObject, stack);
		for (int i = 0; i < listObject.size(); i++) {
			listObject.get(i).setIsDeleted(AnswerFlag.Y.toString());
			objectRepository.save(listObject.get(i));
		}
	}

	private void addChild(IamMsObject root, List<IamMsObject> listObject, Stack<IamMsObject> stack) {
		List<IamMsObject> listChild = this.objectRepository.findByIamMsSystem_SystemIdAndIsDeletedOrderByObjectId(
				root.getIamMsSystem().getSystemId(), AnswerFlag.N.toString());

		stack.pop();
		if (!listChild.isEmpty()) {
			for (IamMsObject list : listChild) {
				if (list.getObjectParent() == root) {
					stack.push(list);
				}
			}

			if (!stack.empty()) {
				listObject.add(stack.peek());
				addChild(stack.peek(), listObject, stack);
			}
		}
	}
	
}
