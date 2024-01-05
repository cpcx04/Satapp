package triana.salesianos.edu.SataApp.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public class UserWorker extends Users {

    private boolean jefe;

    public UserWorker(UUID id, String username, String fullName, String password, String email, boolean validated, Function role, List<Ticket> createdTickets, List<Ticket> assignedTickets, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, LocalDateTime createdAt, LocalDateTime lastPasswordChangeAt, boolean jefe) {
        super(id, username, fullName, password, email, validated, role, createdTickets, assignedTickets, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, createdAt, lastPasswordChangeAt);
        this.jefe = jefe;
    }
}
