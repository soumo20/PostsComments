package fr.postscomments.authentification.login;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/login")
public class LoginController {

    private final LoginUserService loginUserService;

    public LoginController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }


    @PostMapping
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginUserService.login(loginRequest));
    }

}
