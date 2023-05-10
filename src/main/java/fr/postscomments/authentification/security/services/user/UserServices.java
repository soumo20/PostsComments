package fr.postscomments.authentification.security.services.user;

import fr.postscomments.authentification.models.UserApp;

public interface UserServices {

    UserApp findUserConnected();

    void existsByEmail(String email);

    void enableAppUser(String email);

    UserApp saveUser(UserApp userApp);
}
