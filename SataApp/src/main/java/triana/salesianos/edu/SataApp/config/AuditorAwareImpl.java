package triana.salesianos.edu.SataApp.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import triana.salesianos.edu.SataApp.model.Users;

import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication)
                .map(auth -> (Users) auth.getPrincipal())
                .map(Users::getId)
                .map(java.util.UUID::toString);
    }
}
