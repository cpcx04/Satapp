package triana.salesianos.edu.SataApp.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import triana.salesianos.edu.SataApp.dto.user.AddUser;
import triana.salesianos.edu.SataApp.dto.user.Login;
import triana.salesianos.edu.SataApp.dto.user.UserResponse;
import triana.salesianos.edu.SataApp.exception.User.UserValidationException;
import triana.salesianos.edu.SataApp.model.Users;
import triana.salesianos.edu.SataApp.model.UserWorker;
import triana.salesianos.edu.SataApp.security.jwt.JwtProvider;
import triana.salesianos.edu.SataApp.security.jwt.JwtUserResponse;
import triana.salesianos.edu.SataApp.service.UserWorkerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name="User",description = "Controller for user class,it contains methods to control the user(register,login,validation...)")
public class UserController {

    private final UserWorkerService userWorkerService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201 Created", description = "Register was succesful", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = JwtUserResponse.class)), examples = {
                            @ExampleObject(value = """
                                    {
                                        "id": "ff45a024-2acc-4fcf-b99b-ad8e39d8d2f7",
                                        "username": "cristian2",
                                        "email": "user@gmail.com",
                                        "nombre": "Cristian Pulido",
                                        "role": "ROLE_USER",
                                        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmZjQ1YTAyNC0yYWNjLTRmY2YtYjk5Yi1hZDhlMzlkOGQyZjciLCJpYXQiOjE3MDM3OTEzNzYsImV4cCI6MTcwMzg3Nzc3Nn0.Hde4IubfhXswKa8NH5_N_XD1Q6sYuslfU4CrPb6MgmQNA0-6DiyCnGcpna__-IfGo-g-lDgehGGd2mD6emYqrQ"
                                    }
                                                                        """) }) }),
            @ApiResponse(responseCode = "400 Bad Request", description = "Register was not succesful", content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<JwtUserResponse> createNewUser(@Valid @RequestBody AddUser addUser) {
        UserWorker user = userWorkerService.createUser(addUser);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(addUser.username(),addUser.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(user, token));
    }

    @Operation(summary = "Authenticate and generate JWT for user login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login successful", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = JwtUserResponse.class)),
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "id": "4066d638-45c1-43dc-8276-06f20c562f5a",
                                        "username": "cristian3",
                                        "email": "user@gmail.com",
                                        "nombre": "Cristian Pulido",
                                        "role": "ROLE_USER",
                                        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MDY2ZDYzOC00NWMxLTQzZGMtODI3Ni0wNmYyMGM1NjJmNWEiLCJpYXQiOjE3MDM3OTI2MDUsImV4cCI6MTcwMzg3OTAwNX0.wkyYgYf-JSBgtDhYsticdTzJ0PHXyWVl7sGYH2THbTkRMltr_jdbz7eNgdCSggnyiYnmmhR_qKRtYl5j7mZKQg"
                                    }
                                    """
                    ))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> loginUser(@RequestBody Login loginUser) {
        if (!userWorkerService.isUserValidated(loginUser.username())) {
            throw new UserValidationException("Usuario no válido");
        } else {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.username(),
                            loginUser.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(authentication);
            Users user = (Users) authentication.getPrincipal();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(JwtUserResponse.of(user, token));
        }
    }
    @Operation(summary = "Admin Validation for users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Validation successful", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = JwtUserResponse.class)),
                            examples = @ExampleObject(
                                    value = """
                                                    {
                                                         "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
                                                         "username": "cristian1",
                                                         "email": "cristian@example.com",
                                                         "nombre": "Cristian Garcia",
                                                         "role": "ROLE_USER",
                                                         "createdAt": null
                                                     }
                                            """
                            ))
            }),
            @ApiResponse(responseCode = "403", description = "ACCESS DENIED"),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @PutMapping("/users/{uuid}/validate")
    public UserResponse validateAUser(@PathVariable("uuid") UUID uuid) {
        return UserResponse.of(userWorkerService.edit(uuid));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gets all user no validated", content = {
                    @Content(mediaType = "application/json", examples = { @ExampleObject(value = """
                             [
                                 {
                                     "id": "8b6932a7-aeed-4b4c-bc95-98b9a6817851",
                                     "username": "paco2",
                                     "email": "paco@example.com",
                                     "nombre": "Paco Martinez",
                                     "role": "ROLE_USER",
                                     "createdAt": null
                                 },
                                 {
                                     "id": "70a150a3-0a1f-48b3-9fc7-67fba2885c01",
                                     "username": "luismi3",
                                     "email": "luismi@example.com",
                                     "nombre": "Luismi Gonzalez",
                                     "role": "ROLE_USER",
                                     "createdAt": null
                                 },
                                 {
                                     "id": "9a98b20f-8f24-4647-af45-45b0bfe63a11",
                                     "username": "lucia4",
                                     "email": "lucia@example.com",
                                     "nombre": "Lucia Sanchez",
                                     "role": "ROLE_USER",
                                     "createdAt": null
                                 },
                                 {
                                     "id": "c7d3d0ac-3e68-4cd6-8233-df25d5ef95f6",
                                     "username": "manuel5",
                                     "email": "manuel@example.com",
                                     "nombre": "Manuel Perez",
                                     "role": "ROLE_USER",
                                     "createdAt": null
                                 },
                                 {
                                     "id": "f5a2edc4-06d6-4f2d-a9c0-fb72c4a07c7c",
                                     "username": "antonio6",
                                     "email": "antonio@example.com",
                                     "nombre": "Antonio Hernandez",
                                     "role": "ROLE_USER",
                                     "createdAt": null
                                 }
                             ]  
                            """) }) }),
            @ApiResponse(responseCode = "400", description = "Unable to find any user", content = @Content)

    }

    )
    @Operation(summary = "findAll", description = "Find All Users no validated in the database")
    @GetMapping("/users/no-validated")
    public ResponseEntity<List<UserResponse>> findAllUsersNoValidated() {
        List<UserResponse> allNonValidatedUsers = userWorkerService.findAllNonValidatedUsers();

        if (allNonValidatedUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(allNonValidatedUsers);
    }


}
