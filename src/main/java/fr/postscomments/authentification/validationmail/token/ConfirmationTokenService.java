package fr.postscomments.authentification.validationmail.token;

import fr.postscomments.authentification.models.UserApp;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);

    ConfirmationToken getToken(String token);


    String confirmToken(String token);

    void saveConfirmationToken(UserApp userApp, String token);
}
