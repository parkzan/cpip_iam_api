package co.prior.iam.module.param.model.request;

import lombok.Data;

@Data
public class CreateParamInfoRequest {
	
	private String paramCode;
	private String paramEnDesc;
	private String paramLoaclDesc;
	private String paramGroup;

}
