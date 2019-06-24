package co.prior.iam;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import co.prior.iam.model.JwtConstants;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
	            .apiInfo(ApiInfo.DEFAULT)
	            .forCodeGeneration(true)
	            .genericModelSubstitutes(ResponseEntity.class)
	            .securityContexts(Arrays.asList(securityContext()))
	            .securitySchemes(Arrays.asList(apiKey()))
	            .useDefaultResponseMessages(false);
	}

    private ApiKey apiKey() {
        return new ApiKey("JWT", JwtConstants.HEADER_STRING.value(), "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
        		.securityReferences(defaultAuth())
        		.forPaths(PathSelectors.ant("/api/**"))
        		.build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

}
