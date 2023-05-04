package fr.postscomments.authentification.login;

public interface LoginUserService {

    JwtResponse login(LoginRequest loginRequest);

}
