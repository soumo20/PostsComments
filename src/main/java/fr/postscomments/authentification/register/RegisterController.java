package fr.postscomments.authentification.register;

import fr.postscomments.authentification.login.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/registration")
public class RegisterController {
    private final RegisterUserService registerUserService;

    private final RegistrationService registrationService;

    public RegisterController(RegisterUserService registerUserService, RegistrationService registrationService) {
        this.registerUserService = registerUserService;
        this.registrationService = registrationService;
    }


    @PostMapping
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        String token = registrationService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("user created with success. A message of validation is sended to your adresse mail :" + token));
    }

    @GetMapping("/confirm/token={token}")
    public String confirm(@PathVariable("token") String token) {
        return registrationService.confirmToken(token);
    }
}
