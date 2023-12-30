package triana.salesianos.edu.SataApp.controller.inventory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import triana.salesianos.edu.SataApp.dto.inventory.GetInventoryDto;
import triana.salesianos.edu.SataApp.dto.inventory.GetLocationsDto;
import triana.salesianos.edu.SataApp.dto.inventory.GetTypeDto;
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
            @ApiResponse(responseCode = "201", description = "Gets all inventory items", content = {
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
}
