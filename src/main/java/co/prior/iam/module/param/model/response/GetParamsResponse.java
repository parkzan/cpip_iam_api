package co.prior.iam.module.param.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetParamsResponse {
	
    String paramGroup;
    List<ParamInfoData> paramInfoList;

}
