package co.prior.iam.module.param.model.request;

import lombok.Data;

@Data
public class CreateParamGroupRequest {

	private String paramGroup;
	private String paramEnDesc;
	private String paramLoaclDesc;

}
