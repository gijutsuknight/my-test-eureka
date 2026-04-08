package my.test.eureka.client3;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Eureka Client 3 API", version = "1.0.0"))
@SpringBootApplication
public class EurekaClient3Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClient3Application.class, args);
	}

}
