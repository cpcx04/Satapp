package triana.salesianos.edu.SataApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.model.User;
import triana.salesianos.edu.SataApp.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository usuarioRepository;


    public Optional<User> findById(UUID id){
        return usuarioRepository.findById(id);
    }

    public boolean userExists(String username) {
        return usuarioRepository.existsByUsernameIgnoreCase(username);
    }
}
