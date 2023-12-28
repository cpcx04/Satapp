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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import triana.salesianos.edu.SataApp.dto.user.AddUser;
import triana.salesianos.edu.SataApp.dto.user.Login;
import triana.salesianos.edu.SataApp.model.User;
import triana.salesianos.edu.SataApp.model.UserWorker;
import triana.salesianos.edu.SataApp.security.jwt.JwtProvider;
import triana.salesianos.edu.SataApp.security.jwt.JwtUserResponse;
import triana.salesianos.edu.SataApp.service.UserWorkerService;

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

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.username(),
                            loginUser.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(authentication);
            User user = (User) authentication.getPrincipal();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(JwtUserResponse.of(user, token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
