package fr.postscomments.authentification.register;

import fr.postscomments.authentification.login.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class RegisterController {
    private final IRegisterUserService IRegisterUserService;

    public RegisterController(IRegisterUserService IRegisterUserService) {
        this.IRegisterUserService = IRegisterUserService;
    }


    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        IRegisterUserService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
