package co.prior.iam.module.param.service;


import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.entity.IamMsParameterInfo;

import co.prior.iam.error.exception.DataNotFoundException;
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


            ParamRespone respones = new ParamRespone();
            respones.setParamGroup(parameterGroup.getParamGroup());




            return null ;

    }
}
