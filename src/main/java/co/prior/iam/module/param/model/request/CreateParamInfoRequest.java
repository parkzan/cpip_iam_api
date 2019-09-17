package co.prior.iam.module.param.model.request;

import lombok.Data;

@Data
public class CreateParamInfoRequest {
	
	private String paramInfo;
	private String paramEnDesc;
	private String paramLoaclDesc;
	private String paramGroup;

}
