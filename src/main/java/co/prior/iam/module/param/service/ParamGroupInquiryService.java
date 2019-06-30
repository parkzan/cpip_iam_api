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

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "param")
public class ParamGroupInquiryService {

    ParamGroupRepository paramGroupRepository;
    ParamInfoRepository paramInfoRepository;
        public ParamGroupInquiryService(ParamGroupRepository paramGroupRepository ,ParamInfoRepository paramInfoRepository){

            this.paramGroupRepository = paramGroupRepository;
            this.paramInfoRepository = paramInfoRepository;

        }


    @Cacheable
    public List<ParamRespone> inquiryParamGroup() throws Exception{

            List<IamMsParameterGroup> parameterGroups = paramGroupRepository.findByIsDeleted("N");
        List<ParamRespone> list = new ArrayList<>();
            if(!parameterGroups.isEmpty()) {



                for (IamMsParameterGroup group : parameterGroups) {
                    List<IamMsParameterInfo> parameterInfo = paramInfoRepository.findByParamGroup_ParamGroupAndIsDeleted(group.getParamGroup(), "N");
                    List<ParamInfoModel> paramInfoList = new ArrayList<>();
                    ParamRespone respones = new ParamRespone();

                    if (!parameterInfo.isEmpty()) {

                        for (IamMsParameterInfo param : parameterInfo) {

                            ParamInfoModel info = new ParamInfoModel();
                            respones.setParamGroup(group.getParamGroup());
                            info.setParamInfo(param.getParamInfo());
                            info.setParamEnMessage(param.getParamEnDescription());
                            info.setParamLocalMessage(param.getParamLocalDescription());

                            paramInfoList.add(info);
                        }
                        respones.setParamInfo(paramInfoList);


                    }

                    list.add(respones);
                }
                return list;
            }
        throw new DataNotFoundException("99", "data not found");
    }
}
