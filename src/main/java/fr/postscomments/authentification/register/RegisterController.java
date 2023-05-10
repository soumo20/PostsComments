package fr.postscomments.authentification.register;

import fr.postscomments.authentification.login.MessageResponse;
import fr.postscomments.authentification.validationmail.token.ConfirmationTokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/registration")
public class RegisterController {


    private final RegistrationUserServiceImpl registrationUserServiceImpl;

    private final ConfirmationTokenService confirmationTokenService;

    public RegisterController(RegistrationUserServiceImpl registrationUserServiceImpl, ConfirmationTokenService confirmationTokenService) {

        this.registrationUserServiceImpl = registrationUserServiceImpl;
        this.confirmationTokenService = confirmationTokenService;
    }


    @PostMapping
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        String token = registrationUserServiceImpl.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("user created with success. A message of validation is sended to your adresse mail :" + token));
    }

    @GetMapping("/confirm/token={token}")
    public String confirm(@PathVariable("token") String token) {

        return confirmationTokenService.confirmToken(token);
    }
}
