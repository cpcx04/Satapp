package triana.salesianos.edu.SataApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.inventory.GetTypeDto;
import triana.salesianos.edu.SataApp.repository.TypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public List<GetTypeDto> findAll() {
        return typeRepository.findAll()
                .stream()
                .map(GetTypeDto::of)
                .toList();
    }
}
