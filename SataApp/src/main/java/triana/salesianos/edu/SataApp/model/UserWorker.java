package triana.salesianos.edu.SataApp.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserWorker extends User{
    public UserWorker(UUID id, String username, String fullName, String password, String email, boolean validated, Function role, List<Ticket> createdTickets, List<Ticket> assignedTickets, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, LocalDateTime createdAt, LocalDateTime lastPasswordChangeAt) {
        super(id, username, fullName, password, email, validated, role, createdTickets, assignedTickets, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, createdAt, lastPasswordChangeAt);
    }
}
