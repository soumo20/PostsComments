package fr.postsComments.jwtSecurity.repository;

import fr.postsComments.jwtSecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByNameRole( String nameRole);
}
