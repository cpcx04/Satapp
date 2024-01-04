package triana.salesianos.edu.SataApp.dto.Ticket;

import triana.salesianos.edu.SataApp.model.Ticket;

import java.util.UUID;

public record GetTicketDto(

        UUID ticketId,
        String description,
        String status,
        String createdByUsername,
        String assignedTo,
        UUID relatedInventoryItem
) {
    public static GetTicketDto of(Ticket ticket) {
        return new GetTicketDto(
                ticket.getId(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedBy().getFullName(),
                ticket.getAssignedTo().getFullName(),
                ticket.getRelatedInventoryItem().getId()
        );

    }

    public static GetTicketDto fromTicket(Ticket ticket) {
        return new GetTicketDto(
                ticket.getId(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedBy().getFullName(),
                ticket.getAssignedTo().getFullName(),
                ticket.getRelatedInventoryItem().getId()
        );
    }
}
