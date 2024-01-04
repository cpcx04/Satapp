package triana.salesianos.edu.SataApp.controller.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import triana.salesianos.edu.SataApp.dto.Ticket.AddTicketDto;
import triana.salesianos.edu.SataApp.dto.Ticket.GetTicketDto;
import triana.salesianos.edu.SataApp.dto.Ticket.GetTicketsFromInventory;
import triana.salesianos.edu.SataApp.dto.inventory.AddInventoryDto;
import triana.salesianos.edu.SataApp.dto.inventory.GetInventoryDto;
import triana.salesianos.edu.SataApp.exception.Inventory.RelatedTicketsException;
import triana.salesianos.edu.SataApp.model.InventoryItems;
import triana.salesianos.edu.SataApp.model.Ticket;
import triana.salesianos.edu.SataApp.service.TicketService;

import java.util.List;
import java.util.UUID;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The ticket has been edited", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetTicketDto.class)), examples = {
                            @ExampleObject(value = """
                                                   {
                                                    "ticketId": "5fb05a52-eb6d-4d34-9e8d-98e6d01472fc",
                                                    "description": "Ticket 1",
                                                    "status": "Abierto",
                                                    "createdByUsername": "El Administrador",
                                                    "assignedTo": "Luismi Gonzalez",
                                                    "relatedInventoryItem": "3f0190ac-ebef-4fc2-99c9-5d44016da63a"
                                                    }
                                                  """) }) }),
            @ApiResponse(responseCode = "404", description = "Any ticket was found", content = @Content),
    })
    @PutMapping("/ticket/{uuid}")
    @Operation(summary = "Edit a ticket")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetTicketDto> editTicket(@PathVariable UUID uuid, @RequestBody @Valid AddTicketDto e) {
        Ticket ticket = ticketService.editTicket(uuid, e);
        if (ticket != null) {
            GetTicketDto getTicketDto = GetTicketDto.of(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(getTicketDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket delete")
    })
    @Operation(summary = "Delete a Inventory Item", description = "Delete a Ticket checking that the item is in the database saved")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/ticket/{uuid}")
    public ResponseEntity<?> deleteTicketById(@PathVariable UUID uuid) {
        Ticket ticket = ticketService.findById(uuid);

        ticketService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gets all ticket items", content = {
                    @Content(mediaType = "application/json", examples = { @ExampleObject(value = """
                            [
                                {
                                    "ticketId": "5fb05a52-eb6d-4d34-9e8d-98e6d01472fc",
                                    "description": "Ticket 1",
                                    "status": "Abierto",
                                    "createdByUsername": "El Administrador",
                                    "assignedTo": "Manuel Perez",
                                    "relatedInventoryItem": "3f0190ac-ebef-4fc2-99c9-5d44016da63a"
                                },
                                {
                                    "ticketId": "fb25f398-1363-48d5-a695-4cf0ef67592f",
                                    "description": "Ticket 2",
                                    "status": "En progreso",
                                    "createdByUsername": "El Administrador",
                                    "assignedTo": "Manuel Perez",
                                    "relatedInventoryItem": "84c21a5b-d4d9-4419-af41-82cb21416856"
                                }
                            ]
                            """) }) }),
            @ApiResponse(responseCode = "400", description = "Unable to find any ticket", content = @Content)

    }

    )
    @Operation(summary = "findAll", description = "Find All Ticket in the database")
    @GetMapping("/ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GetTicketDto>> findAllTickets() {
        List<GetTicketDto> allTicket = ticketService.findAll();

        if (allTicket.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(allTicket);
    }

    @Operation(summary = "Gets  the tickets form a inventariable of the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The tickets has been found", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetTicketsFromInventory.class)), examples = {
                            @ExampleObject(value = """
                                    {
                                        "itemId": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
                                        "tickets": [
                                            {
                                                "ticketId": "5fb05a52-eb6d-4d34-9e8d-98e6d01472fc",
                                                "description": "Ticket 1",
                                                "status": "Abierto",
                                                "createdByUsername": "El Administrador",
                                                "assignedTo": "Manuel Perez",
                                                "relatedInventoryItem": "3f0190ac-ebef-4fc2-99c9-5d44016da63a"
                                            },
                                            {
                                                "ticketId": "48849dcb-f57c-4a80-a2d1-192fff14746e",
                                                "description": "Ordenador no funciona",
                                                "status": "Abierto",
                                                "createdByUsername": "El Administrador",
                                                "assignedTo": "Luismi Gonzalez",
                                                "relatedInventoryItem": "3f0190ac-ebef-4fc2-99c9-5d44016da63a"
                                            }
                                        ]
                                    }
                                                         """) }) }),
            @ApiResponse(responseCode = "404", description = "Unable to find any tickets with that id.", content = @Content),
    })
    @GetMapping("/ticket/inventariable/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetTicketsFromInventory> findTicketsById(@PathVariable UUID uuid) {
        GetTicketsFromInventory ticketsFromInventory = ticketService.findAllTicketsInInventory(uuid);

        if (ticketsFromInventory.tickets().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(ticketsFromInventory);
    }
}
