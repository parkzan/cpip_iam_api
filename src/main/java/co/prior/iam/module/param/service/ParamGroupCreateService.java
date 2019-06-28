package co.prior.iam.module.param.service;

import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.module.param.model.ParamGroupCreateModel;
import co.prior.iam.repository.ParamGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ParamGroupCreateService {

    ParamGroupRepository paramGroupRepository ;

    public ParamGroupCreateService(ParamGroupRepository paramGroupRepository) {
        this.paramGroupRepository = paramGroupRepository;
    }

    @Transactional
    public void createParamGroup(ParamGroupCreateModel paramGroupCreateModel){
        Optional<IamMsParameterGroup> iamMsParameterGroup = paramGroupRepository.findByParamGroupAndIsDeleted(paramGroupCreateModel.getParamGroup(),"N");

        if (iamMsParameterGroup.isPresent()){
            throw new DataDuplicateException("99",paramGroupCreateModel.getParamGroup()+" duplicate");
        }else {
            IamMsParameterGroup model = new IamMsParameterGroup();
            model.setParamGroup(paramGroupCreateModel.getParamGroup());
            model.setParamEnDescription(paramGroupCreateModel.getParamEnDesc());
            model.setParamLocalDescription(paramGroupCreateModel.getParamLoaclDesc());

            paramGroupRepository.save(model);
        }

    }

}
