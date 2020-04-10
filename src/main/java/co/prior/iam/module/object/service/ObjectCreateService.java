package co.prior.iam.module.object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsParameterInfo;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.ParamInfoRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class ObjectCreateService {

    private final ObjectRepository objectRepository;
    private final SystemRepository systemRepository;
    private final ParamInfoRepository paramRepository;

    public ObjectCreateService(ObjectRepository objectRepository, SystemRepository systemRepository, ParamInfoRepository paramRepository) {
        this.objectRepository = objectRepository;
        this.systemRepository = systemRepository;
        this.paramRepository = paramRepository;
    }

    @Transactional
    public void createObject(ObjectCreateReq objectCreateReq) {
        log.info("Service createObject: {}", objectCreateReq);

        IamMsSystem iamMsSystem = this.systemRepository.findBySystemIdAndIsDeleted(
                objectCreateReq.getSystemId(), AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.SYSTEM_NOT_FOUND));

        Optional<IamMsObject> iamMsObject = this.objectRepository.findByIamMsSystemAndObjectCodeAndIsDeleted(
                iamMsSystem, objectCreateReq.getObjectCode(), AnswerFlag.N.toString());

        IamMsParameterInfo type = this.paramRepository.findByParamCodeAndIsDeleted(objectCreateReq.getType(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR));

        IamMsObject parentObject = null;
        if (objectCreateReq.getObjectParentId() > 0) {
            parentObject = this.objectRepository.findByIamMsSystem_SystemIdAndObjectIdAndIsDeleted(
                    objectCreateReq.getSystemId(), objectCreateReq.getObjectParentId(), AnswerFlag.N.toString())
                    .orElseThrow(() -> new DataNotFoundException(ErrorCode.OBJECT_NOT_FOUND));
        }

        if (iamMsObject.isPresent()) {
            throw new DataDuplicateException(ErrorCode.OBJECT_DUPLICATED);
        }

        IamMsObject model = new IamMsObject();
        model.setObjectCode(objectCreateReq.getObjectCode());
        model.setObjectName(objectCreateReq.getObjectName());
        model.setIamMsSystem(iamMsSystem);
        model.setObjectParent(parentObject);
        model.setObjectUrl(objectCreateReq.getUrl());
        model.setObjectType(type);
        model.setSortingOrder(objectCreateReq.getSort());
        model.setUpdatedBy(null);
        model.setUpdatedDate(null);
        this.objectRepository.save(model);
    }

}
