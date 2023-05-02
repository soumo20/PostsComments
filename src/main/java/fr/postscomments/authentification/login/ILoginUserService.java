package fr.postscomments.authentification.login;

public interface ILoginUserService {

    JwtResponse login(LoginRequest loginRequest);

}
