package co.prior.iam.module.param.service;


import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.repository.ParamGroupRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "{param_group}")
public class ParamGroupInquiryService {

    ParamGroupRepository paramGroupRepository;

        public ParamGroupInquiryService(ParamGroupRepository paramGroupRepository){

            this.paramGroupRepository = paramGroupRepository;

        }


    @Cacheable
    public List<IamMsParameterGroup> inquiryParamGroup() throws Exception{

            List<IamMsParameterGroup> list = paramGroupRepository.findByIsDeleted("N");

            if (list.isEmpty()){
                throw new DataNotFoundException("99","data not found");
            }
            return list ;

    }
}
