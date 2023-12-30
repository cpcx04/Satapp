package triana.salesianos.edu.SataApp.dto.inventory;

import triana.salesianos.edu.SataApp.model.Type;

import java.util.UUID;

public record GetTypeDto(
        UUID id,
        String type,
        int price,
        boolean cedido) {

    public static GetTypeDto of(Type type) {
        return new GetTypeDto(
                type.getId(),
                type.getType().toString(),
                type.getPrice(),
                type.isCedido()
        );
    }
}

