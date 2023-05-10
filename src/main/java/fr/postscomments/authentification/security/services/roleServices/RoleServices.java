package fr.postscomments.authentification.security.services.roleServices;

import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;

import java.util.Set;

public interface RoleServices {
    public Set<Role> unificationRoles(UserApp userApp);
}
