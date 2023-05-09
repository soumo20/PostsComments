package fr.postscomments.authentification.register;

import fr.postscomments.authentification.login.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class RegisterController {
    private final RegisterUserService registerUserService;

    private final RegistrationService registrationService;

    public RegisterController(RegisterUserService registerUserService, RegistrationService registrationService) {
        this.registerUserService = registerUserService;
        this.registrationService = registrationService;
    }


    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        registerUserService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
