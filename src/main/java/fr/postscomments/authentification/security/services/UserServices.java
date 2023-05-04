package fr.postscomments.authentification.security.services;

import fr.postscomments.authentification.models.UserApp;

public interface UserServices {

    UserApp findUserConnected();
}
