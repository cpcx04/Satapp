package triana.salesianos.edu.SataApp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Login(

        @NotBlank(message = "{login.username.notblank}")
        String username,

        @NotBlank(message = "{login.password.notblank}")
        @Size(min = 6, message = "{login.password.size}")
        String password
) {
}
