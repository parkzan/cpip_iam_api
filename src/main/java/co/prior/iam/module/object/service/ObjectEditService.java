package co.prior.iam.module.object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsParameterInfo;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.ParamInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ObjectEditService {

    private final ObjectRepository objectRepository;
    private final ParamInfoRepository paramRepository;

    public ObjectEditService(ObjectRepository objectRepository, ParamInfoRepository paramRepository) {
        this.objectRepository = objectRepository;
        this.paramRepository = paramRepository;
    }

    @Transactional
    public void editObject(ObjectEditReq objectEditReq) {
        log.info("Service editObject: {}", objectEditReq);

        IamMsObject iamMsObject = this.objectRepository.findByObjectId(objectEditReq.getObjectId())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.OBJECT_NOT_FOUND));

        IamMsParameterInfo type = this.paramRepository.findByParamCodeAndIsDeleted(objectEditReq.getType(), AnswerFlag.N.toString())
                .orElseThrow(() -> new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR));

        iamMsObject.setObjectName(objectEditReq.getNewName());
        iamMsObject.setObjectType(type);
        iamMsObject.setObjectUrl(objectEditReq.getUrl());
        iamMsObject.setSortingOrder(objectEditReq.getSort());


        this.objectRepository.save(iamMsObject);
    }

}
