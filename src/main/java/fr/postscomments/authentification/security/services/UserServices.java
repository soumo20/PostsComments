package fr.postscomments.authentification.security.services;

import fr.postscomments.authentification.models.UserApp;

public interface UserServices {

    UserApp findUserConnected();

    boolean existsByEmail(String email);

    public int enableAppUser(String email);

    UserApp saveUser(UserApp userApp);
}
