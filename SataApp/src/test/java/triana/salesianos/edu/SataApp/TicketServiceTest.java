package triana.salesianos.edu.SataApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import triana.salesianos.edu.SataApp.dto.Ticket.AddTicketDto;
import triana.salesianos.edu.SataApp.repository.TicketRepository;
import triana.salesianos.edu.SataApp.service.TicketService;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketRepository ticketRepository;



    @Test
    @WithMockUser(value = "admin1",password = "admin1")
    void createAticket(){
        List<AddTicketDto> ticketDtos = List.of(
                (       new AddTicketDto("Ticket 1", "In progress", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"), "admin1", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"))),
                new AddTicketDto("Ticket 2", "In progress", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"), "admin1", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3")),
                new AddTicketDto("Ticket 3", "In progress", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"), "admin1", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3")),
                new AddTicketDto("Ticket 4", "In progress", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"), "admin1", UUID.fromString("839e2b39-361e-4cc1-866f-f52bd9d812c3"))
        );

        //Precondiciones
        //Mockito.when(ticketRepository.findAll()).thenReturn(ticketDtos);
    }

}
