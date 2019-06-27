package co.prior.iam.model;

public enum AnswerFlag {

	Y("yes"),
	N("no");
	
	private String desc;
	
	private AnswerFlag(String desc) {
		this.desc = desc;
	}
	
	public String desc() {
		return this.desc;
	}
	
}
