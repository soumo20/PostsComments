package fr.postsComments.authentification.login;

public interface LoginUserService {

    public JwtResponse login(LoginRequest loginRequest);

}
