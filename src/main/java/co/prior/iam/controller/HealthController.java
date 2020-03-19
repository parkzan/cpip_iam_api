package co.prior.iam.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Value("${api.name}")
    private String name;

    @GetMapping("/")
    public String redirectUi() {
        return "Hello, IAM Service (" + name + ")";
    }

}
