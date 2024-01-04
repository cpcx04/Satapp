package triana.salesianos.edu.SataApp.dto.Ticket;

import java.util.UUID;

public record AddTicketDto(
        String description,
        String status,
        UUID createdBy,
        String assignedTo,
        UUID relatedInventoryItem
) {
}
