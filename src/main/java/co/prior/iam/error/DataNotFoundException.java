package co.prior.iam.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

    private long status ;
    private String message;

   public DataNotFoundException(String exception){
       super(exception);
   }
   public DataNotFoundException(long status , String message){
       this.status = status ;
       this.message = message;

   }
}
