package fr.postsComments.authentification.login;

public interface ILoginUserService {

    public JwtResponse login(LoginRequest loginRequest);

}
