package triana.salesianos.edu.SataApp.controller.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import triana.salesianos.edu.SataApp.dto.Ticket.AddTicketDto;
import triana.salesianos.edu.SataApp.dto.Ticket.GetTicketDto;
import triana.salesianos.edu.SataApp.model.Ticket;
import triana.salesianos.edu.SataApp.service.TicketService;

@RestController
@RequiredArgsConstructor
@Tag(name="Ticket",description = "Controller for control the tickets,get,delete,create and edits tickets")
public class TicketController {

    private final TicketService ticketService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creation of a new ticket", content = {
                    @Content(mediaType = "application/json", examples = { @ExampleObject(value =
                            """
                                            {
                                                "uuid": "13018be5-9760-4be7-baeb-cdec48d77fc9",
                                                "description": "Ordenador no funciona",
                                                "status": "Abierto",
                                                "createdByUsername": "Cristian Garcia",
                                                "assignedTo": "Luismi Gonzalez",
                                                "relatedInventoryItem": "3f0190ac-ebef-4fc2-99c9-5d44016da63a"
                                            }
                                    """) }) }),
            @ApiResponse(responseCode = "400", description = "The creation of the ticket has not been done", content = @Content)

    }

    )
    @PostMapping("/ticket")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "addTicket", description = "Create a new Ticket")
    public ResponseEntity<GetTicketDto> addTicket(@Valid @RequestBody AddTicketDto ticketDto) {
        Ticket ticket = ticketService.newTicket(ticketDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GetTicketDto.of(ticket));
    }
}
