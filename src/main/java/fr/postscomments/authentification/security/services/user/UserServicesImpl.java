package fr.postscomments.authentification.security.services.user;

import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.UserRepository;
import fr.postscomments.authentification.security.services.role.RoleServices;
import fr.postscomments.shared.exceptions.EntityAlreadyExist;
import fr.postscomments.shared.exceptions.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;

    private final RoleServices roleServices;

    private final PasswordEncoder encoder;

    public UserServicesImpl(UserRepository userRepository, RoleServices roleServices, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleServices = roleServices;
        this.encoder = encoder;
    }

    @Override
    public UserApp findUserConnected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new EntityNotFoundException("No user"));
    }

    @Override
    public void existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EntityAlreadyExist("Email already token");
        }
    }

    @Override
    public void enableAppUser(String email) {
        userRepository.enableAppUser(email);
    }

    @Override
    public UserApp saveUser(UserApp userApp) {

        Set<Role> roles = roleServices.unificationRoles(userApp);

        UserApp user = new UserApp(userApp.getEmail()
                , encoder.encode(userApp.getPassword()), userApp.getPhone(), roles);


        return userRepository.save(user);
    }
}
