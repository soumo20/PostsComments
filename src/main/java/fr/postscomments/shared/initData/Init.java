package fr.postscomments;

import fr.postscomments.authentification.models.ERole;
import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.RoleRepository;
import fr.postscomments.authentification.repository.UserRepository;
import fr.postscomments.posts.models.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class Init implements CommandLineRunner {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Environment env;
    @Autowired
    RoleRepository roleRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role role1 = new Role();
        role1.setNameRole(ERole.ROLE_USER);
        Role role2 = new Role();
        role2.setNameRole(ERole.ROLE_ADMIN);
        UserApp userApp = new UserApp();
        userApp.setEmail("camille@gmail.com");
        userApp.setPassword(encoder.encode("azerty"));
        userApp.setPhone("0672142332");
        userApp.setRoles(Set.of(role1));

        Post post = new Post();
        post.setTitle("Test post");
        post.setContent("Test content");
        post.setAuthor(userApp);

        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(userApp);
        entityManager.persist(post);
        entityManager.flush();



    }
}

