package fr.postscomments.authentification.security.services;

import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.UserRepository;
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

    private final RolesServices rolesServices;

    private final PasswordEncoder encoder;

    public UserServicesImpl(UserRepository userRepository, RolesServices rolesServices, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.rolesServices = rolesServices;
        this.encoder = encoder;
    }

    @Override
    public UserApp findUserConnected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new EntityNotFoundException("No user"));
    }

    @Override
    public boolean existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EntityAlreadyExist("Email already token");
        }
        return false;
    }

    @Override
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    @Override
    public UserApp saveUser(UserApp userApp) {

        Set<Role> roles = rolesServices.unificationRoles(userApp);

        UserApp user = new UserApp(userApp.getEmail()
                , encoder.encode(userApp.getPassword()), userApp.getPhone(), roles);


        return userRepository.save(user);
    }
}
