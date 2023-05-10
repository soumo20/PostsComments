package fr.postscomments.authentification.security.services;

import fr.postscomments.authentification.models.ERole;
import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.RoleRepository;
import fr.postscomments.shared.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class RolesServicesImpl implements RolesServices {
    private final RoleRepository roleRepository;


    private static String errorMessage = "Role not found";

    public RolesServicesImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Set<Role> unificationRoles(UserApp userApp) {
        Set<Role> strRoles = userApp.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findOneByNameRole(ERole.ROLE_USER).orElseThrow(() -> new EntityNotFoundException(errorMessage));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.getNameRole().name().equals("admin")) {
                    Role adminRole = roleRepository.findOneByNameRole(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new EntityNotFoundException(errorMessage));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findOneByNameRole(ERole.ROLE_USER)
                            .orElseThrow(() -> new EntityNotFoundException(errorMessage));
                    roles.add(userRole);
                }
            });
        }
        return roles;
    }
}
