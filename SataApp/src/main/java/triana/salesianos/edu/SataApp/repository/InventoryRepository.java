package triana.salesianos.edu.SataApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import triana.salesianos.edu.SataApp.model.InventoryItems;

import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItems, UUID> {

    //Esto mas para adelante será cambiado para borrar por un numero, dejo la consulta realizada para actualizarla despues
    //si lo dejase así sería mas facil buscar por UUID y luego borrar sin necesidad de consulta
    @Modifying
    @Query("DELETE FROM InventoryItems  i WHERE i.id = :id")
    void deleteByUUID(UUID id);
}
