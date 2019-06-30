package co.prior.iam.module.param.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.entity.IamMsParameterInfo;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.param.model.request.CreateParamGroupRequest;
import co.prior.iam.module.param.model.request.CreateParamInfoRequest;
import co.prior.iam.module.param.model.response.GetParamsResponse;
import co.prior.iam.module.param.model.response.ParamInfoData;
import co.prior.iam.repository.ParamGroupRepository;
import co.prior.iam.repository.ParamInfoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParamService {

	private final ParamGroupRepository paramGroupRepository;
	private final ParamInfoRepository paramInfoRepository;

	public ParamService(ParamGroupRepository paramGroupRepository, ParamInfoRepository paramInfoRepository) {
		this.paramGroupRepository = paramGroupRepository;
		this.paramInfoRepository = paramInfoRepository;
	}

	@Cacheable(cacheNames = "params")
	public List<GetParamsResponse> getParams() {
		log.info("Service getParams");
		
		List<IamMsParameterGroup> iamMsParameterGroups= this.paramGroupRepository.findByIsDeleted(AnswerFlag.N.toString());
		if (iamMsParameterGroups.isEmpty()) {
			throw new DataNotFoundException("99", "data not found");
		}
		
		List<GetParamsResponse> params = new ArrayList<>();
		for (IamMsParameterGroup paramGroup : iamMsParameterGroups) {
			List<ParamInfoData> paramInfoList = new ArrayList<>();
			for (IamMsParameterInfo paramInfo : paramGroup.getParamInfoSet()) {
				paramInfoList.add(ParamInfoData.builder()
						.paramInfo(paramInfo.getParamInfo())
						.paramEnMessage(paramInfo.getParamEnDescription())
						.paramLocalMessage(paramInfo.getParamLocalDescription())
						.build());
			}
			params.add(GetParamsResponse.builder()
					.paramGroup(paramGroup.getParamGroup())
					.paramInfoList(paramInfoList)
					.build());
		}
		
		return params;
	}
	
	@Transactional
	public void createParamGroup(CreateParamGroupRequest request) {
		log.info("Service createParamGroup paramGroup: {}", request.getParamGroup());

		if (this.paramGroupRepository.existsByParamGroupAndIsDeleted(request.getParamGroup(), AnswerFlag.N.toString())) {
			throw new DataDuplicateException("99", "param group is duplicated");
		}

		IamMsParameterGroup iamMsParameterGroup = IamMsParameterGroup.builder()
				.paramGroup(request.getParamGroup())
				.paramEnDescription(request.getParamEnDesc())
				.paramLocalDescription(request.getParamLoaclDesc())
				.build();

		this.paramGroupRepository.save(iamMsParameterGroup);
		this.refreshParams();
	}

	@Transactional
	public void createParamInfo(CreateParamInfoRequest request) {
		log.info("Service createParamInfo paramInfo: {}", request.getParamInfo());
		
		if (this.paramInfoRepository.existsByParamInfoAndIsDeleted(request.getParamInfo(), AnswerFlag.N.toString())) {
			throw new DataDuplicateException("99", "param info is duplicated");
		}
		
		IamMsParameterGroup iamMsParameterGroup = this.paramGroupRepository.findByParamGroupAndIsDeleted(
				request.getParamGroup(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException("99", "param group not found"));

		this.paramInfoRepository.save(IamMsParameterInfo.builder()
				.paramInfo(request.getParamInfo())
				.paramEnDescription(request.getParamEnDesc())
				.paramLocalDescription(request.getParamLoaclDesc())
				.paramGroup(iamMsParameterGroup)
				.build());
		
		this.refreshParams();
	}
	
	@CacheEvict(value = "params", allEntries = true)
	public void refreshParams() {
		log.info("Service refreshParams");
		
		// refresh parameters
	}

}
