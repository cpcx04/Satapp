package triana.salesianos.edu.SataApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triana.salesianos.edu.SataApp.model.EquipmentType;
import triana.salesianos.edu.SataApp.model.Type;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TypeRepository extends JpaRepository<Type, UUID> {
    Optional<Type> findByType(EquipmentType type);

}
