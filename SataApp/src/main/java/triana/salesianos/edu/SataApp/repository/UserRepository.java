package triana.salesianos.edu.SataApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triana.salesianos.edu.SataApp.model.Users;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findFirstByUsername(String username);

    Optional<Users> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
}
