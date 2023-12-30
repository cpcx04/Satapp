package triana.salesianos.edu.SataApp.dto.inventory;

import triana.salesianos.edu.SataApp.model.Location;

public record GetLocationsDto(
        String pasillo,
        String planta,
        String nombre) {

    public static GetLocationsDto of(Location location) {
        return new GetLocationsDto(
                location.getPasillo(),
                location.getPlanta(),
                location.getNombre()
        );
    }
}

