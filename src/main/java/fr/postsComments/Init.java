package fr.postsComments;

import fr.postsComments.authentification.models.ERole;
import fr.postsComments.authentification.models.Role;
import fr.postsComments.authentification.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    @Autowired
    IRoleRepository roleRepo;

    @Override
    public void run(String... args) throws Exception {
        Role role1 = new Role();
        role1.setNameRole(ERole.ROLE_USER);
        Role role2 = new Role();
        role2.setNameRole(ERole.ROLE_ADMIN);
        try {
            roleRepo.save(role1);
            roleRepo.save(role2);
        } catch (Exception ex) {
            System.out.println("Error persisting roles: " + ex.getMessage());
        }
    }
}

