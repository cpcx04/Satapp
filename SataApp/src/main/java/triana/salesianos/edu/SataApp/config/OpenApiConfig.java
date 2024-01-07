package triana.salesianos.edu.SataApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SataApp for Schools")
                        .version("1.0")
                        .description("App to control the equipment of a school")
                        .license(new License().name("Cristian Pulido General License").url("https://github.com/cpcx04/Satapp")));
    }

}
