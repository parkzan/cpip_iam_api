package co.prior.iam.module.param.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.prior.iam.model.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.entity.IamMsParameterInfo;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
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
			throw new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		List<GetParamsResponse> params = new ArrayList<>();
		for (IamMsParameterGroup paramGroup : iamMsParameterGroups) {
			List<ParamInfoData> paramInfoList = new ArrayList<>();
			List<IamMsParameterInfo> parameterInfos = this.paramInfoRepository.findByParamGroup_ParamGroupAndIsDeleted(paramGroup.getParamGroup() , AnswerFlag.N.toString());
			if(!parameterInfos.isEmpty()){
				for (IamMsParameterInfo paramInfo : parameterInfos) {
					paramInfoList.add(ParamInfoData.builder()
							.paramInfoId(paramInfo.getParamInfoId())
							.paramInfo(paramInfo.getParamCode())
							.paramEnMessage(paramInfo.getParamEnDescription())
							.paramLocalMessage(paramInfo.getParamLocalDescription())
							.build());
				}
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
			throw new DataDuplicateException(ErrorCode.INTERNAL_SERVER_ERROR);
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
		log.info("Service createParamInfo paramCode: {}", request.getParamInfo());
		
		if (this.paramInfoRepository.existsByParamCodeAndIsDeleted(request.getParamInfo(), AnswerFlag.N.toString())) {
			throw new DataDuplicateException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		IamMsParameterGroup iamMsParameterGroup = this.paramGroupRepository.findByParamGroupAndIsDeleted(
				request.getParamGroup(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR));

		this.paramInfoRepository.save(IamMsParameterInfo.builder()
				.paramCode(request.getParamInfo())
				.paramEnDescription(request.getParamEnDesc())
				.paramLocalDescription(request.getParamLoaclDesc())
				.paramGroup(iamMsParameterGroup)
				.build());
		
		this.refreshParams();
	}
	
	@CacheEvict(value = "params", allEntries = true)
	public void refreshParams() {
		log.info("Service refreshParams");
		
		// clear all parameter cache
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<ParamInfoData> getErrorMessage(ErrorCode errorCode) {
		log.info("Service getErrorMessage errorCode: {}", errorCode);
		
		Optional<GetParamsResponse> paramOpt = this.getParams().stream()
				.filter(param -> ParamGroup.ERROR_MESSAGE.toString().equalsIgnoreCase(param.getParamGroup()))
				.findFirst();
		
		if (paramOpt.isPresent()) {
			GetParamsResponse param = paramOpt.get();
			log.info("param {}",param);
			return param.getParamInfoList().stream()
					.filter(paramInfo -> errorCode.code().equalsIgnoreCase(paramInfo.getParamInfo()))
					.findFirst();
		}
		
		return Optional.empty();
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<ParamInfoData> getUserType(UserType userType) {
		log.info("Service getUserType errorCode: {}", userType);

		Optional<GetParamsResponse> paramOpt = this.getParams().stream()
				.filter(param -> ParamGroup.USER_TYPE.toString().equalsIgnoreCase(param.getParamGroup()))
				.findFirst();

		if (paramOpt.isPresent()) {
			GetParamsResponse param = paramOpt.get();
			return param.getParamInfoList().stream()
					.filter(paramInfo -> userType.type().equalsIgnoreCase(paramInfo.getParamInfo()))
					.findFirst();
		}

		return Optional.empty();
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<ParamInfoData> getSystemConfig(SystemConfig value) {
		log.info("Service getSystemConfig: {}", value);

		Optional<GetParamsResponse> paramOpt = this.getParams().stream()
				.filter(param -> ParamGroup.SYSTEM_CONFIG.toString().equalsIgnoreCase(param.getParamGroup()))
				.findFirst();

		if (paramOpt.isPresent()) {
			GetParamsResponse param = paramOpt.get();
			return param.getParamInfoList().stream()
					.filter(paramInfo -> value.value().equalsIgnoreCase(paramInfo.getParamInfo()))
					.findFirst();
		}

		return Optional.empty();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<ParamInfoData> getObjectType(ObjectType value) {
		log.info("Service getObjectType: {}", value);

		Optional<GetParamsResponse> paramOpt = this.getParams().stream()
				.filter(param -> ParamGroup.OBJECT_TYPE.toString().equalsIgnoreCase(param.getParamGroup()))
				.findFirst();

		if (paramOpt.isPresent()) {
			GetParamsResponse param = paramOpt.get();
			return param.getParamInfoList().stream()
					.filter(paramInfo -> value.value().equalsIgnoreCase(paramInfo.getParamInfo()))
					.findFirst();
		}

		return Optional.empty();
	}

	public IamMsParameterInfo findByParamCodeAndIsDeleted(String paramcode){
		IamMsParameterInfo info = this.paramInfoRepository.findByParamCodeAndIsDeleted(paramcode,AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR));

		return info;
	}


}
