package daae.learner;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@SpringBootApplication
@EnableSwagger2
public class LearnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnerApplication.class, args);

	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("daae.learner"))
				.paths(PathSelectors.any())
				.build()
                .apiInfo(apiInfo());
	}
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Learner REST API",
                "Endpoints for make trainings and predictions",
                "1.0",
                "Terms of service",
                new Contact("CDS", "www.cds.com.py", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
