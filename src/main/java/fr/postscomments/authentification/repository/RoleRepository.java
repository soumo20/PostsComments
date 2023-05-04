package fr.postscomments.authentification.repository;

import fr.postscomments.authentification.models.ERole;
import fr.postscomments.authentification.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findOneByNameRole(ERole eRole);
}
