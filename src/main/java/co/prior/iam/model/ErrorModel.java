package co.prior.iam.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorModel {

	private ErrorCode code;
	private String messageEn;
	private String messageTh;

}
