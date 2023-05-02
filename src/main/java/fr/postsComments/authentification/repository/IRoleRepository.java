package fr.postsComments.authentification.repository;

import fr.postsComments.authentification.models.ERole;
import fr.postsComments.authentification.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findOneByNameRole(ERole eRole);
}
