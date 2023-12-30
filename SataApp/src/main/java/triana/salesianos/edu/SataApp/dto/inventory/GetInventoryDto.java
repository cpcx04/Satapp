package triana.salesianos.edu.SataApp.dto.inventory;

import triana.salesianos.edu.SataApp.model.InventoryItems;
import triana.salesianos.edu.SataApp.model.Ticket;

import java.util.List;
import java.util.UUID;

public record GetInventoryDto(
        UUID id,
        String type,
        String description,
        String status,
        String location,
        String additionalDetails,
        List<UUID> relatedTickets
) {
    public static GetInventoryDto of(InventoryItems inventoryItem) {
        return new GetInventoryDto(
                inventoryItem.getId(),
                inventoryItem.getType() != null ? inventoryItem.getType().getType().toString() : null,
                inventoryItem.getDescription(),
                inventoryItem.getStatus(),
                inventoryItem.getLocation().getNombre(),
                inventoryItem.getAdditionalDetails(),
                inventoryItem.getRelatedTickets().stream().map(Ticket::getId).toList()
        );
    }

}
