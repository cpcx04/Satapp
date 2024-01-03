package triana.salesianos.edu.SataApp.dto.inventory;

import triana.salesianos.edu.SataApp.model.EquipmentType;

public record AddInventoryDto(

        EquipmentType type,
        String description,
        String status,
        String location,
        String additionalDetails
){
}
