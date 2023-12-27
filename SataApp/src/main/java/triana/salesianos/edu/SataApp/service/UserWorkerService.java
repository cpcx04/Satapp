package triana.salesianos.edu.SataApp.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import triana.salesianos.edu.SataApp.dto.user.AddUser;
import triana.salesianos.edu.SataApp.model.UserWorker;
import triana.salesianos.edu.SataApp.repository.UserWorkerRepository;

@Service
@AllArgsConstructor
public class UserWorkerService {

    private final UserWorkerRepository userWorkerRepository;
    private final PasswordEncoder passwordEncoder;
    public UserWorker createUser(AddUser addUser){
        UserWorker user = new UserWorker();
        user.setUsername(addUser.username());
        user.setPassword(passwordEncoder.encode(addUser.password()));
        user.setFullName(addUser.fullName());
        user.setEmail(addUser.email());

        return userWorkerRepository.save(user);
    }
}
