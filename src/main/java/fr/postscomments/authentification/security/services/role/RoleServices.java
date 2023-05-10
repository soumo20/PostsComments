package fr.postscomments.authentification.security.services.role;

import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;

import java.util.Set;

public interface RoleServices {
    Set<Role> unificationRoles(UserApp userApp);
}
