package co.prior.iam.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorModel {

	private String code;
	private String message;

}
