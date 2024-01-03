package triana.salesianos.edu.SataApp.controller.ticket;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="Ticket",description = "Controller for control the tickets,get,delete,create and edits tickets")
public class TicketController {
}
