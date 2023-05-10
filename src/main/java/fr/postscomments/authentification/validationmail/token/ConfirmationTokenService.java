package fr.postscomments.authentification.validationmail.token;

import fr.postscomments.authentification.models.UserApp;

import java.util.Optional;

public interface ConfirmationTokenService {
    public void saveConfirmationToken(ConfirmationToken token);

    public Optional<ConfirmationToken> getToken(String token);

    public int setConfirmedAt(String token);


    public String confirmToken(String token);

    public void saveConfirmationToken(UserApp userApp, String token);
}
