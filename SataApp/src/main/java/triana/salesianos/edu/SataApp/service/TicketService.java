package triana.salesianos.edu.SataApp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.Ticket.AddTicketDto;
import triana.salesianos.edu.SataApp.exception.Ticket.NotOwnerOfTicketException;
import triana.salesianos.edu.SataApp.model.InventoryItems;
import triana.salesianos.edu.SataApp.model.Ticket;
import triana.salesianos.edu.SataApp.model.User;
import triana.salesianos.edu.SataApp.repository.InventoryRepository;
import triana.salesianos.edu.SataApp.repository.TicketRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final InventoryRepository inventoryRepository;


    public Ticket newTicket(AddTicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setDescription(ticketDto.description());
        ticket.setStatus(ticketDto.status());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> optionalUser = userService.findByUsername(currentUsername);
        if (optionalUser.isPresent()) {
            User createdBy = optionalUser.get();
            ticket.setCreatedBy(createdBy);
        } else {
            throw new EntityNotFoundException();
        }

        Optional<User> assignedTo = userService.findByUsername(ticketDto.assignedTo());
        if (assignedTo.isPresent()) {
            ticket.setAssignedTo(assignedTo.get());
        } else {
           throw new EntityNotFoundException();
        }

        Optional<InventoryItems> optionalInventoryItem = inventoryRepository.findById(ticketDto.relatedInventoryItem());
        if (optionalInventoryItem.isPresent()) {
            InventoryItems inventoryItem = optionalInventoryItem.get();
            ticket.setRelatedInventoryItem(inventoryItem);
        } else {
            throw new EntityNotFoundException();
        }
        return ticketRepository.save(ticket);
    }

    public Ticket editTicket(UUID uuid, AddTicketDto e) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Optional<Ticket> optionalTicket = ticketRepository.findById(uuid);

        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();

            if (!ticket.getCreatedBy().getUsername().equals(currentUsername) && !userService.isAdmin(currentUsername)) {
                throw new NotOwnerOfTicketException("Only ticket creator or admin can edit a ticket");
            }

            if (e.description() != null) {
                ticket.setDescription(e.description());
            }

            if (e.status() != null) {
                ticket.setStatus(e.status());
            }

            if (e.assignedTo()!= null) {
                Optional<User> assignedToUser = userService.findByUsername(e.assignedTo());
                assignedToUser.ifPresent(ticket::setAssignedTo);
            }

            if (e.relatedInventoryItem() != null) {
                Optional<InventoryItems> relatedItem = inventoryRepository.findById(e.relatedInventoryItem());
                relatedItem.ifPresent(ticket::setRelatedInventoryItem);
            }

            return ticketRepository.save(ticket);
        }
        return null;
    }
}
