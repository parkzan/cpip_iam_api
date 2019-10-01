package co.prior.iam.model;

public enum  UserType {
    CENTRALIZE("Centralize"),
    ENTREPRENEUR("Entrepreneur"),
    PROVINCE("Province");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String type(){
        return type;
    }
}
