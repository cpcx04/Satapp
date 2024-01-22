package triana.salesianos.edu.SataApp.controller.inventory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import triana.salesianos.edu.SataApp.dto.inventory.AddInventoryDto;
import triana.salesianos.edu.SataApp.dto.inventory.GetInventoryDto;
import triana.salesianos.edu.SataApp.dto.inventory.GetLocationsDto;
import triana.salesianos.edu.SataApp.dto.inventory.GetTypeDto;
import triana.salesianos.edu.SataApp.exception.Inventory.RelatedTicketsException;
import triana.salesianos.edu.SataApp.model.InventoryItems;
import triana.salesianos.edu.SataApp.service.InventoryService;
import triana.salesianos.edu.SataApp.service.LocationService;
import triana.salesianos.edu.SataApp.service.TypeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name="Inventory",description = "Controller for control the inventory,get,delete,create and edits inventory items")
public class InventoryController {

    private final InventoryService inventoryService;
    private final LocationService locationService;
    private final TypeService typeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gets all inventory items", content = {
                    @Content(mediaType = "application/json", examples = { @ExampleObject(value = """
                            [
                                {
                                    "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
                                    "type": "LAPTOP",
                                    "description": "Un portatil ligero y versatil, perfecto para movilidad y tareas diarias.",
                                    "status": "Disponible",
                                    "location": "1DAM",
                                    "additionalDetails": "Portatil con procesador Intel Core i7, 16 GB RAM, SSD de 512 GB y pantalla Full HD de 15 pulgadas.",
                                    "relatedTickets": [
                                        "5fb05a52-eb6d-4d34-9e8d-98e6d01472fc"
                                    ]
                                },
                                {
                                    "id": "84c21a5b-d4d9-4419-af41-82cb21416856",
                                    "type": "DESKTOP",
                                    "description": "Una estacion de trabajo potente y versatil para uso diario.",
                                    "status": "En uso",
                                    "location": "2DAM",
                                    "additionalDetails": "Sobremesa con procesador AMD Ryzen 7, 32 GB RAM, SSD de 1 TB, tarjeta grafica NVIDIA GeForce RTX 3060 y monitor 27 pulgadas.",
                                    "relatedTickets": [
                                        "fb25f398-1363-48d5-a695-4cf0ef67592f"
                                    ]
                                }
                            ]
                            """) }) }),
            @ApiResponse(responseCode = "400", description = "Unable to find any items", content = @Content)

    }

    )
    @Operation(summary = "findAll", description = "Find All Items in the database")
    @GetMapping("/inventariable")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<GetInventoryDto>> findAllInventoryItems() {
        List<GetInventoryDto> allInventory = inventoryService.findAll();

        if (allInventory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(allInventory);
    }

    @Operation(summary = "Gets a specific items of the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item has been found", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetInventoryDto.class)), examples = {
                            @ExampleObject(value = """
                                    {
                                        "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
                                        "type": "LAPTOP",
                                        "description": "Un portatil ligero y versatil, perfecto para movilidad y tareas diarias.",
                                        "status": "Disponible",
                                        "location": "1DAM",
                                        "additionalDetails": "Portatil con procesador Intel Core i7, 16 GB RAM, SSD de 512 GB y pantalla Full HD de 15 pulgadas.",
                                        "relatedTickets": [
                                            "5fb05a52-eb6d-4d34-9e8d-98e6d01472fc"
                                        ]
                                    }
                                                         """) }) }),
            @ApiResponse(responseCode = "404", description = "Unable to find a item with that id.", content = @Content),
    })
    @GetMapping("/inventariable/{uuid}")
    public GetInventoryDto findItemById(@PathVariable UUID uuid) {
        return GetInventoryDto.of(inventoryService.findById(uuid));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gets all locations", content = {
                    @Content(mediaType = "application/json", examples = { @ExampleObject(value = """
                            [
                                {
                                    "pasillo": "Pasillo 1",
                                    "planta": "Planta Baja",
                                    "nombre": "Primero DAM"
                                },
                                {
                                    "pasillo": "Pasillo 2",
                                    "planta": "Planta Alta",
                                    "nombre": "Segundo DAM"
                                }
                            ]
                            """) }) }),
            @ApiResponse(responseCode = "400", description = "Unable to find any locations", content = @Content)

    }

    )
    @Operation(summary = "findAll", description = "Find All Locations in the database")
    @GetMapping("/inventariable/ubicaciones")
    public ResponseEntity<List<GetLocationsDto>> findLocations(){

        List<GetLocationsDto> allLocations = locationService.findAll();

        if (allLocations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(allLocations);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gets all types", content = {
                    @Content(mediaType = "application/json", examples = { @ExampleObject(value = """
                            [
                                {
                                    "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
                                    "type": "LAPTOP",
                                    "price": 1000,
                                    "cedido": false
                                },
                                {
                                    "id": "84c21a5b-d4d9-4419-af41-82cb21416856",
                                    "type": "DESKTOP",
                                    "price": 1500,
                                    "cedido": false
                                }
                            ]
                            """) }) }),
            @ApiResponse(responseCode = "400", description = "Unable to find any types in database", content = @Content)

    }

    )
    @Operation(summary = "findAll", description = "Find All Types in the database")
    @GetMapping("/inventariable/tipos")
    public ResponseEntity<List<GetTypeDto>> findTypes(){

        List<GetTypeDto> allTypes = typeService.findAll();

        if (allTypes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(allTypes);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creation of a new inventariable", content = {
                    @Content(mediaType = "application/json", examples = { @ExampleObject(value =
                            """
                                            {
                                                "id": "f917c40e-f0c3-4724-82a6-0bc7a65a5c51",
                                                "type": "LAPTOP",
                                                "description": "ASUS-19",
                                                "status": "Active",
                                                "location": "Segundo DAM",
                                                "additionalDetails": "Asus with intel core i7 and a incredible gaming graphics",
                                                "relatedTickets": []
                                            }
                                    """) }) }),
            @ApiResponse(responseCode = "400", description = "The creation of the inventariable has not been done", content = @Content)

    }

    )
    @PostMapping("/inventariable")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "addInventoryItem", description = "Create a new InventoryItem")
    public ResponseEntity<GetInventoryDto> addInventariable(@Valid @RequestBody AddInventoryDto inventoryDto) {
        InventoryItems e = inventoryService.newInventariable(inventoryDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GetInventoryDto.of(e));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The item has been edited", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetInventoryDto.class)), examples = {
                            @ExampleObject(value = """
                                    {
                                        "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
                                        "type": "DESKTOP",
                                        "description": "Un portatil ligero y versatil, perfecto para movilidad y tareas diarias.",
                                        "status": "Disponible",
                                        "location": "Segundo DAM",
                                        "additionalDetails": "Portatil con procesador Intel Core i7, 16 GB RAM, SSD de 512 GB y pantalla Full HD de 15 pulgadas.",
                                        "relatedTickets": [
                                            "5fb05a52-eb6d-4d34-9e8d-98e6d01472fc"
                                        ]
                                    }     
                                                   """) }) }),
            @ApiResponse(responseCode = "404", description = "Any item was found", content = @Content),
    })
    @PutMapping("/inventariable/{uuid}")
    @Operation(summary = "Edit a Item")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetInventoryDto> editInventory(@PathVariable UUID uuid, @RequestBody @Valid AddInventoryDto e) {
        InventoryItems inventoryItems = inventoryService.editInventory(uuid, e);
        if (inventoryItems != null) {
            GetInventoryDto getInventoryDto = GetInventoryDto.of(inventoryItems);
            return ResponseEntity.status(HttpStatus.OK).body(getInventoryDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item delete")
    })
    @Operation(summary = "Delete a Inventory Item", description = "Delete a InvetoryItem checking that the item is in the database saved")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/inventariable/{uuid}")
    public ResponseEntity<?> deleteItemById(@PathVariable UUID uuid) {
        InventoryItems inventoryItems = inventoryService.findById(uuid);

        if (inventoryItems.getRelatedTickets() != null && !inventoryItems.getRelatedTickets().isEmpty()) {
            throw new RelatedTicketsException();
        }

        inventoryService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
