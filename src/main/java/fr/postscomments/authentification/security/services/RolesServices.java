package fr.postscomments.authentification.security.services;

import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;

import java.util.Set;

public interface RolesServices {
    public Set<Role> unificationRoles(UserApp userApp);
}
