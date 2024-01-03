package triana.salesianos.edu.SataApp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.inventory.AddInventoryDto;
import triana.salesianos.edu.SataApp.dto.inventory.GetInventoryDto;
import triana.salesianos.edu.SataApp.model.InventoryItems;
import triana.salesianos.edu.SataApp.model.Location;
import triana.salesianos.edu.SataApp.model.Type;
import triana.salesianos.edu.SataApp.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final TypeService typeService;
    private final LocationService locationService;

    public List<GetInventoryDto> findAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(GetInventoryDto::of)
                .toList();
    }
    public InventoryItems findById(UUID uuid) {
        return inventoryRepository.findById(uuid).orElseThrow(()->new EntityNotFoundException("Inventory item with ID " + uuid + " not found"));
    }

    public InventoryItems newInventariable(AddInventoryDto nuevo) {
        InventoryItems inventariable = new InventoryItems();

        inventariable.setDescription(nuevo.description());
        inventariable.setStatus(nuevo.status());
        inventariable.setAdditionalDetails(nuevo.additionalDetails());

        Type type = typeService.findByName(nuevo.type().toString());
        Location location = locationService.findByName(nuevo.location());

        inventariable.setType(type);
        inventariable.setLocation(location);

        return inventoryRepository.save(inventariable);
    }
    public InventoryItems editInventory(UUID uuid, AddInventoryDto e) {
        Optional<InventoryItems> optionalInventory = inventoryRepository.findById(uuid);

        if (optionalInventory.isPresent()) {
            InventoryItems inventoryItem = optionalInventory.get();

            if (e.description() != null) {
                inventoryItem.setDescription(e.description());
            }

            if (e.status() != null) {
                inventoryItem.setStatus(e.status());
            }

            if (e.additionalDetails() != null) {
                inventoryItem.setAdditionalDetails(e.additionalDetails());
            }

            if (e.type() != null) {
                Type type = typeService.findByName(e.type().toString());
                if (type != null) {
                    inventoryItem.setType(type);
                }
            }

            if (e.location() != null) {
                Location location = locationService.findByName(e.location());
                if (location != null) {
                    inventoryItem.setLocation(location);
                }
            }

            return inventoryRepository.save(inventoryItem);
        }
        return null;
    }

    @Transactional
    public String delete(UUID id) {
        inventoryRepository.deleteByUUID(id);
        return "Eliminado con éxito";
    }
}
