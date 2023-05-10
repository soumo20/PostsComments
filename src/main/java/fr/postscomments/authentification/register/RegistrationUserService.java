package fr.postscomments.authentification.register;

import fr.postscomments.authentification.models.UserApp;

public interface RegistrationUserService {
    public String register(SignUpRequest request);

    public String registreUserService(UserApp userApp);
}
