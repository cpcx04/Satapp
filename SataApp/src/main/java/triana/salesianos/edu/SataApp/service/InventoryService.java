package triana.salesianos.edu.SataApp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.inventory.GetInventoryDto;
import triana.salesianos.edu.SataApp.model.InventoryItems;
import triana.salesianos.edu.SataApp.repository.InventoryRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<GetInventoryDto> findAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(GetInventoryDto::of)
                .toList();
    }
    public InventoryItems findById(UUID uuid) {
        return inventoryRepository.findById(uuid).orElseThrow(()->new EntityNotFoundException("Inventory item with ID " + uuid + " not found"));
    }
}
