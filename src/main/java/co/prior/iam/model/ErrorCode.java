package co.prior.iam.model;

public enum ErrorCode {

	INTERNAL_SERVER_ERROR("E9999"),
	UNAUTHORIZED("E0199"),
	ACCESS_DENIED("E0198"),
	INVALID_JWT_SIGNATURE("E0197"),
	INVALID_JWT_TOKEN("E0196"),
	EXPIRED_JWT_TOKEN("E0195"),
	UNSUPPORTED_JWT_TOKEN("E0194"),
	JWT_CLAIM_EMPTY("E0193"),
	USER_OR_PASSWORD_INCORRECT("E0100"),
	USER_DISABLED("E0101"),
	USER_LOCKED("E0102"),
	PASSWORD_INCORRECT("E0103"),
	INVALID_REFRESH_TOKEN("E0104"),
	USER_NOT_FOUND("E0200"),
	USER_DUPLICATED("E0201"),
	USER_ROLE_NOT_FOUND("E0202"),
	USER_ROLE_DUPLICATED("E0203"),
	SYSTEM_NOT_FOUND("E0300"),
	SYSTEM_DUPLICATED("E0301"),
	OBJECT_NOT_FOUND("E0400"),
	OBJECT_DUPLICATED("E0401"),
	ROLE_NOT_FOUND("E0500"),
	ROLE_DUPLICATED("E0501"),
	ROLE_OBJECT_NOT_FOUND("E0502");
	
	private String code;
	
	private ErrorCode(String code) {
		this.code = code;
	}
	
	public String code() {
		return this.code;
	}
	
}