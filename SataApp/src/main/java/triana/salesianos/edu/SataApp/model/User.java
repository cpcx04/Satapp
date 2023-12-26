package triana.salesianos.edu.SataApp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_entity")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String username;

    private String fullName;

    private String password;

    private String email;

    private boolean validated;

    @Enumerated(EnumType.STRING)
    private Function role;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> createdTickets;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> assignedTickets;
}
