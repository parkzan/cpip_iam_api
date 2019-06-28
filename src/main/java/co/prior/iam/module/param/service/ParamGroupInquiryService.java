package co.prior.iam.module.param.service;


import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.entity.IamMsParameterInfo;

import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.module.param.model.ParamInfoModel;
import co.prior.iam.module.param.model.ParamRespone;
import co.prior.iam.repository.ParamGroupRepository;
import co.prior.iam.repository.ParamInfoRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "{param_group}")
public class ParamGroupInquiryService {

    ParamGroupRepository paramGroupRepository;
    ParamInfoRepository paramInfoRepository;
        public ParamGroupInquiryService(ParamGroupRepository paramGroupRepository ,ParamInfoRepository paramInfoRepository){

            this.paramGroupRepository = paramGroupRepository;
            this.paramInfoRepository = paramInfoRepository;

        }


    @Cacheable
    public ParamRespone inquiryParamGroup(String paramGroup) throws Exception{

            IamMsParameterGroup parameterGroup = paramGroupRepository.findByParamGroupAndIsDeleted(paramGroup,"N")
                    .orElseThrow(() -> new DataNotFoundException("99" , "data not found"));

            List<IamMsParameterInfo> parameterInfo = paramInfoRepository.findByParamGroup_ParamGroupAndIsDeleted(parameterGroup.getParamGroup(),"N");
            List<ParamInfoModel> paramInfoList = new ArrayList<>();
            ParamRespone respones = new ParamRespone();
            if(!parameterInfo.isEmpty()){

                for(IamMsParameterInfo param : parameterInfo){

                    ParamInfoModel info = new ParamInfoModel();
                    respones.setParamGroup(parameterGroup.getParamGroup());
                    info.setParamInfo(param.getParamInfo());
                    info.setParamEnMessage(param.getParamEnDescription());
                    info.setParamLocalMessage(param.getParamLocalDescription());

                    paramInfoList.add(info);
                }
                respones.setParamInfo(paramInfoList);
                return respones;
            }


            throw new DataNotFoundException("99","data not found");
    }
}
