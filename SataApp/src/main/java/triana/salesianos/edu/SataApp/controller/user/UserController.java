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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import triana.salesianos.edu.SataApp.dto.user.AddUser;
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
                                                                        }
                                                                        """) }) }),
            @ApiResponse(responseCode = "400 Bad Request", description = "Register was not succesful", content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<JwtUserResponse> createUserWithUserBike(@Valid @RequestBody AddUser addUser) {
        UserWorker user = userWorkerService.createUser(addUser);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(addUser.username(),addUser.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(user, token));
    }

}
