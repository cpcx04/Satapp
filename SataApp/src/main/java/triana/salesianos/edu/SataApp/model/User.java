package triana.salesianos.edu.SataApp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user_entity")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements UserDetails {

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


    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_";
        if (role=="ADMIN") {
            role += "ADMIN";
        } else {
            role += "USER";
        }
        return List.of(new SimpleGrantedAuthority(role));
    }
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
