package triana.salesianos.edu.SataApp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.user.AddUser;
import triana.salesianos.edu.SataApp.dto.user.UserResponse;
import triana.salesianos.edu.SataApp.exception.User.UserValidationException;
import triana.salesianos.edu.SataApp.model.UserWorker;
import triana.salesianos.edu.SataApp.repository.UserWorkerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserWorkerService {

    private final UserWorkerRepository userWorkerRepository;
    private final PasswordEncoder passwordEncoder;
    public UserWorker createUser(AddUser addUser){
        UserWorker user = new UserWorker();
        user.setUsername(addUser.username());
        user.setPassword(passwordEncoder.encode(addUser.password()));
        user.setRole(user.getRole());
        user.setFullName(addUser.fullName());
        user.setEmail(addUser.email());

        return userWorkerRepository.save(user);
    }
    public boolean isUserValidated(String username) {
        UserWorker user = userWorkerRepository.findByUsername(username);
        return user != null && user.isValidated();
    }

    public UserWorker edit(UUID uuid) {
        Optional<UserWorker> optionalUser = userWorkerRepository.findById(uuid);

        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }

        UserWorker user = optionalUser.get();

        if (!user.isValidated()) {
            user.setValidated(true);
            return userWorkerRepository.save(user);
        } else {
            throw new UserValidationException("Usuario ya validado");
        }
    }

    public List<UserResponse> findAllNonValidatedUsers() {
        List<UserWorker> users = userWorkerRepository.findAll();

        List<UserResponse> nonValidatedUsers = new ArrayList<>();
        for (UserWorker user : users) {
            if (!user.isValidated()) {
                nonValidatedUsers.add(UserResponse.of(user));
            }
        }

        return nonValidatedUsers;
    }


}
