package co.prior.iam.module.object.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.object.model.request.ObjectDeleteReq;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObjectDeleteService {

	ObjectRepository objectRepository;
	SystemRepository systemRepository;

	public ObjectDeleteService(ObjectRepository objectRepository, SystemRepository systemRepository) {

		this.objectRepository = objectRepository;
		this.systemRepository = systemRepository;

	}

	@Transactional
	public void deleteObject(ObjectDeleteReq objectDeleteReq) {
		log.info("Service deleteObject: {}", objectDeleteReq);

		IamMsObject root = objectRepository
				.findByIamMsSystem_SystemIdAndObjectIdAndIsDeleted(objectDeleteReq.getSystemId(),
						objectDeleteReq.getObjectId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException("data not found"));
		List<IamMsObject> listObject = new ArrayList<>();
		Stack<IamMsObject> stack = new Stack<>();

		listObject.add(root);
		stack.push(root);

		addChild(root, listObject, stack);

		for (int i = 0; i < listObject.size(); i++) {
			listObject.get(i).setIsDeleted(AnswerFlag.Y.toString());
			objectRepository.save(listObject.get(i));
		}

	}

	private void addChild(IamMsObject root, List<IamMsObject> listObject, Stack<IamMsObject> stack) {
		List<IamMsObject> listChild = objectRepository
				.findByIamMsSystem_SystemIdAndIsDeleted(root.getIamMsSystem().getSystemId(), AnswerFlag.N.toString());

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
