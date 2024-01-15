package triana.salesianos.edu.SataApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import triana.salesianos.edu.SataApp.dto.Ticket.AddTicketDto;
import triana.salesianos.edu.SataApp.model.Ticket;
import triana.salesianos.edu.SataApp.repository.TicketRepository;
import triana.salesianos.edu.SataApp.service.TicketService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketRepository ticketRepository;



    @Test
    @WithMockUser(username = "admin1", password = "admin1", roles = "ADMIN")
    void createAticket(){
        AddTicketDto ticketDto = new AddTicketDto("Ticket 1", "In progress", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"), "admin1", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"));


        when(ticketRepository.save(any(Ticket.class))).thenReturn(new Ticket());

        Ticket result = ticketService.newTicket(ticketDto);

        // Verificar que el ticket se haya guardado correctamente
        assertEquals("Ticket 1", result.getDescription());
        assertEquals("In progress", result.getStatus());



    }

}
