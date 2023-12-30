package triana.salesianos.edu.SataApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItems {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    private String description;

    private String status;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private String additionalDetails;

    @OneToMany(mappedBy = "relatedInventoryItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> relatedTickets;
}
