package co.prior.iam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IAMApplication {

	public static void main(String[] args) {
		SpringApplication.run(IAMApplication.class, args);
	}
}
