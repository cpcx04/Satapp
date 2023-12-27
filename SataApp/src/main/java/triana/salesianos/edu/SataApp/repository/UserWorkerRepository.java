package triana.salesianos.edu.SataApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triana.salesianos.edu.SataApp.model.UserWorker;

import java.util.UUID;

@Repository
public interface UserWorkerRepository extends JpaRepository<UserWorker, UUID> {
}
