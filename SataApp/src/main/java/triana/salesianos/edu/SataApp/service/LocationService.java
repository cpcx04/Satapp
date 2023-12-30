package triana.salesianos.edu.SataApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.inventory.GetLocationsDto;
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
}
