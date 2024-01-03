package triana.salesianos.edu.SataApp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.inventory.GetTypeDto;
import triana.salesianos.edu.SataApp.model.EquipmentType;
import triana.salesianos.edu.SataApp.model.Type;
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
    public Type findByName(String typeName) {
        return typeRepository.findByType(EquipmentType.valueOf(typeName))
                .orElseThrow(() -> new EntityNotFoundException("Type with name " + typeName + " not found"));
    }
}
