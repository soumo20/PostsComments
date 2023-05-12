package fr.postscomments.authentification.register;

import fr.postscomments.authentification.models.UserApp;

public interface RegistrationUserService {
    void register(SignUpRequest request);

    String registerUserService(UserApp userApp);
}
