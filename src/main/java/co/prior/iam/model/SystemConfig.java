package co.prior.iam.model;

public enum  SystemConfig {

    NO_OF_FAIL_TIME("NO_OF_FAIL_TIME");

    private String value;

    SystemConfig(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
