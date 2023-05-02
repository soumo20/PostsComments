package fr.postscomments;

import fr.postscomments.authentification.models.ERole;
import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.IRoleRepository;
import fr.postscomments.authentification.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Init implements CommandLineRunner {

    @Autowired
    IRoleRepository roleRepo;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Role role1 = new Role();
        role1.setNameRole(ERole.ROLE_USER);
        Role role2 = new Role();
        role2.setNameRole(ERole.ROLE_ADMIN);
        UserApp userApp = new UserApp();
        userApp.setEmail("camille@gmail.com");
        userApp.setPasseword(encoder.encode("azerty"));
        userApp.setPhone("0672142332");
        userApp.setRoles(Set.of(role1));

        try {
            roleRepo.save(role1);
            roleRepo.save(role2);
            userRepository.save(userApp);
        } catch (Exception ex) {
            System.out.println("Error persisting roles: " + ex.getMessage());
        }
    }
}

