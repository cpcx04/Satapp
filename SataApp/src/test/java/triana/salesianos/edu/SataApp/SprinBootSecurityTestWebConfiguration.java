package triana.salesianos.edu.SataApp;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import triana.salesianos.edu.SataApp.model.Function;
import triana.salesianos.edu.SataApp.model.UserWorker;

import java.util.List;
import java.util.UUID;

@TestConfiguration
public class SprinBootSecurityTestWebConfiguration {

    @Bean("CustomUserDetailsService")
    @Primary
    public UserDetailsService userDetailsService() {
        UserWorker admin = UserWorker.builder()
                .id(UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"))
                .username("admin")
                .password("admin")
                .validated(true)
                .role(Function.valueOf("ADMIN"))
                .build();

        UserWorker user = UserWorker.builder()
                .username("user")
                .password("user")
                .validated(true)
                .role(Function.valueOf("USER"))
                .build();

        return new InMemoryUserDetailsManager(List.of(
                admin, user
        ));
    }
}
