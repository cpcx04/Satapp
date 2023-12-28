package triana.salesianos.edu.SataApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triana.salesianos.edu.SataApp.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
}
