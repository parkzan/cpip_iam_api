package co.prior.iam.model;

public enum  ObjectType {

    MENU("MENU"),
    ACTION("ACTION");

    private String value;

    ObjectType(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
