package triana.salesianos.edu.SataApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import triana.salesianos.edu.SataApp.model.Ticket;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    @Modifying
    @Query("DELETE FROM Ticket  i WHERE i.id = :uuid")
    void deleteByUUID(UUID uuid);
}
