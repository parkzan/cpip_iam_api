package co.prior.iam.module.param.model;

import co.prior.iam.entity.IamMsParameterInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ParamRespone {
    String paramGroup;
    List<ParamInfoModel> paramInfo;


}
