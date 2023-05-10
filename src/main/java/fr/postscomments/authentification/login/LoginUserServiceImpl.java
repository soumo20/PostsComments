package fr.postscomments.authentification.login;

import fr.postscomments.authentification.register.RegistrationService;
import fr.postscomments.authentification.repository.UserRepository;
import fr.postscomments.authentification.security.jwt.JwtUtils;
import fr.postscomments.authentification.security.services.UserDetailsImpl;
import fr.postscomments.shared.exceptions.EntityNotValidate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public LoginUserServiceImpl(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        if (userRepository.isEnable(loginRequest.getEmail())) {
            System.out.println("Hi");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .toList();

            return new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
        } else {
            throw new EntityNotValidate("Please valid your email");
        }
    }

}
