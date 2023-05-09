package fr.postscomments.authentification.register;

import fr.postscomments.authentification.models.ERole;
import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.RoleRepository;
import fr.postscomments.authentification.repository.UserRepository;
import fr.postscomments.authentification.validationmail.token.ConfirmationToken;
import fr.postscomments.authentification.validationmail.token.ConfirmationTokenService;
import fr.postscomments.shared.exceptions.EntityAlreadyExist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final ConfirmationTokenService confirmationTokenService;

    private static final String ERROR_ROLE_NOT_FOUND = "Error: Role is not found.";

    public RegisterUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String register(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            UserApp userExisted = userRepository.findByEmail(signUpRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not founded"));
            if (!userExisted.getEnabled()) {
                String token = UUID.randomUUID().toString();

                //A method to save user and token in this class
                saveConfirmationToken(userExisted, token);
                return token;
            }
            throw new EntityAlreadyExist("user already exist");
        }
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findOneByNameRole(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findOneByNameRole(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findOneByNameRole(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
                    roles.add(userRole);
                }
            });
        }

        // Create new user's account
        UserApp user = new UserApp(signUpRequest.getEmail()
                , encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhone(), roles);

        userRepository.save(user);

        //Creating a token from UUID
        String token = UUID.randomUUID().toString();

        //Getting the confirmation token and then saving it
        saveConfirmationToken(user, token);
        return token;
    }

    private void saveConfirmationToken(UserApp userApp, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), userApp);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);

    }

}
