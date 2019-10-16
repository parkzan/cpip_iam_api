package co.prior.iam.model;

public enum  UserType {
    CENTRALIZE("CENTRAL_USER"),
    ENTREPRENEUR("ENTREPRENEUR_USER"),
    PROVINCE("PROVINCE_USER"),
    ADMIN("IAM_ADMIN");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String type(){
        return type;
    }
}
