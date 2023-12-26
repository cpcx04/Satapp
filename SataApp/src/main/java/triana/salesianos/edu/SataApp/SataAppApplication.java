package triana.salesianos.edu.SataApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(description = "App for take the control of the equipment of a school",
		version = "1.0",
		contact = @Contact(email = "pulidocabellochristian@gmail.com", name = "SataApp by Cristian Pulido"),
		license = @License(name = "SataApp"),
		title = "An app to take control"
)
)
public class SataAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SataAppApplication.class, args);
	}

}
