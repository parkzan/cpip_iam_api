package co.prior.iam.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorModel {
    private long status;
    private String message;

    public ErrorModel(long status, String message){

        this.status = status;
        this.message = message;


    }
}
