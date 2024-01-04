package triana.salesianos.edu.SataApp.dto.Ticket;

import triana.salesianos.edu.SataApp.model.Ticket;

import java.util.List;
import java.util.UUID;

public record GetTicketsFromInventory(

        UUID itemId,

        List<GetTicketDto>tickets

) {
    public static GetTicketsFromInventory of(UUID itemId, List<GetTicketDto> tickets) {
        return new GetTicketsFromInventory(itemId, tickets);
    }


}
