package triana.salesianos.edu.SataApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import triana.salesianos.edu.SataApp.dto.Ticket.GetTicketDto;
import triana.salesianos.edu.SataApp.model.InventoryItems;
import triana.salesianos.edu.SataApp.model.Ticket;
import triana.salesianos.edu.SataApp.model.UserWorker;
import triana.salesianos.edu.SataApp.repository.TicketRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TicketRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TicketRepository ticketRepository;

    @Test
    void findAllTicketsAssignedToUser(){

        InventoryItems i1 = InventoryItems.builder()
                .description("Merda")
                .build();


        UserWorker u1 = UserWorker.builder()
                .fullName("Cristian Cabello")
                .username("cristian2")
                .build();

        UserWorker u2 = UserWorker.builder()
                .fullName("Cristian Pulido")
                .username("cristian3")
                .build();


        Ticket t1 = Ticket.builder()
                .description("Ticket 1")
                .createdBy(u1)
                        .assignedTo(u1)
                .relatedInventoryItem(i1)
                                .build();

        Ticket t2 = Ticket.builder()
                .description("Ticket 2")
                .createdBy(u1)
                .assignedTo(u1)
                .relatedInventoryItem(i1)
                .build();
        Ticket t3 = Ticket.builder()
                .description("Ticket 3")
                .createdBy(u1)
                .assignedTo(u1)
                .relatedInventoryItem(i1)
                .build();
        Ticket t4 = Ticket.builder()
                .description("Ticket 4")
                .createdBy(u2)
                .assignedTo(u2)
                .relatedInventoryItem(i1)
                .build();


        entityManager.persist(u1);
        entityManager.persist(u2);

        entityManager.persist(t1);
        entityManager.persist(t2);
        entityManager.persist(t3);
        entityManager.persist(t4);

        List<GetTicketDto> resultado = ticketRepository.findAllTicketsAssignedToUser(u1.getUsername());

        assertEquals(3,resultado.size());


    }

}
