package co.prior.iam.module.user.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class UserObjectRespone {


    private List<String> objects;
}
