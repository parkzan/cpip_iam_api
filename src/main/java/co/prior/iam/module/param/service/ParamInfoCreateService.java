package co.prior.iam.module.param.service;

import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.entity.IamMsParameterInfo;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.module.param.model.ParamInfoCreateModel;
import co.prior.iam.repository.ParamGroupRepository;
import co.prior.iam.repository.ParamInfoRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "param")
public class ParamInfoCreateService {

    ParamInfoRepository paramInfoRepository;
    ParamGroupRepository paramGroupRepository;

    public ParamInfoCreateService(ParamInfoRepository paramInfoRepository, ParamGroupRepository paramGroupRepository) {
        this.paramInfoRepository = paramInfoRepository;
        this.paramGroupRepository = paramGroupRepository;
    }

    @CacheEvict(value = "param" , allEntries = true)
    public void  createParamInfo(ParamInfoCreateModel paramInfoCreateModel) throws Exception{

        Optional<IamMsParameterInfo> model = paramInfoRepository.findByParamInfoAndIsDeleted(paramInfoCreateModel.getParamInfo() , "N");
        IamMsParameterGroup parameterGroup = paramGroupRepository.findByParamGroupAndIsDeleted(paramInfoCreateModel.getParamGroup() , "N")
                .orElseThrow(()-> new DataNotFoundException("99","data not found"));
        if (!model.isPresent()){

            IamMsParameterInfo iamMsParameterInfo = new IamMsParameterInfo();

            iamMsParameterInfo.setParamInfo(paramInfoCreateModel.getParamInfo());
            iamMsParameterInfo.setParamEnDescription(paramInfoCreateModel.getParamEnDesc());
            iamMsParameterInfo.setParamLocalDescription(paramInfoCreateModel.getParamLoaclDesc());
            iamMsParameterInfo.setParamGroup(parameterGroup);

            paramInfoRepository.save(iamMsParameterInfo);

        }
        else throw new DataDuplicateException("99",paramInfoCreateModel.getParamInfo()+" duplicate");


    }


}
