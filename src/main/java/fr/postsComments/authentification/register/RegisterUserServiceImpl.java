package fr.postsComments.authentification.register;

import fr.postsComments.authentification.models.ERole;
import fr.postsComments.authentification.models.Role;
import fr.postsComments.authentification.models.UserApp;
import fr.postsComments.authentification.repository.IRoleRepository;
import fr.postsComments.authentification.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class RegisterUserServiceImpl implements IRegisterUserService {

    private final IUserRepository IUserRepository;

    private final IRoleRepository IRoleRepository;

    private final PasswordEncoder encoder;

    public RegisterUserServiceImpl(IUserRepository IUserRepository, IRoleRepository IRoleRepository, PasswordEncoder encoder) {
        this.IUserRepository = IUserRepository;
        this.IRoleRepository = IRoleRepository;
        this.encoder = encoder;
    }

    public void register(SignUpRequest signUpRequest) {
        if (IUserRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("user already exist");
        }
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = IRoleRepository.findOneByNameRole(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = IRoleRepository.findOneByNameRole(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = IRoleRepository.findOneByNameRole(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        // Create new user's account
        UserApp user = new UserApp(signUpRequest.getEmail()
                , encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhone(), roles);

        IUserRepository.save(user);
    }
}
