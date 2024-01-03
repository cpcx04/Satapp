package triana.salesianos.edu.SataApp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.inventory.GetLocationsDto;
import triana.salesianos.edu.SataApp.model.Location;
import triana.salesianos.edu.SataApp.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<GetLocationsDto> findAll() {
        return locationRepository.findAll()
                .stream()
                .map(GetLocationsDto::of)
                .toList();
    }

    public Location findByName(String locationName) {
        return locationRepository.findByNombre(locationName)
                .orElseThrow(() -> new EntityNotFoundException("Location with name " + locationName + " not found"));
    }
}
