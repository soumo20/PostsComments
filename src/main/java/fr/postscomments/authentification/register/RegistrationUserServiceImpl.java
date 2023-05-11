package fr.postscomments.authentification.register;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.security.services.user.UserServices;
import fr.postscomments.authentification.validationmail.email.EmailSender;
import fr.postscomments.authentification.validationmail.email.EmailService;
import fr.postscomments.authentification.validationmail.token.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationUserServiceImpl implements RegistrationUserService {
    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final UserServices userServices;

    public RegistrationUserServiceImpl(EmailSender emailSender, ConfirmationTokenService confirmationTokenService, EmailService emailService, UserServices userServices) {
        this.emailSender = emailSender;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
        this.userServices = userServices;
    }

    @Override
    @Transactional
    public void register(SignUpRequest request) {

        String tokenForNewUser = registerUserService(new UserApp(request.getEmail(),
                request.getPassword(), request.getPhone(), request.getRoles()));

        //Since, we are running the spring boot application in localhost, we are hardcoding the
        //url of the server. We are creating a POST request with token param
        // TODO add baseurl as var env
        String link = "http://localhost:8080/api/auth/registration/confirm/token=" + tokenForNewUser;
        emailSender.sendEmail(request.getEmail(), emailService.buildEmail(request.getEmail(), link));

    }

    @Override
    public String registerUserService(UserApp userApp) {

        userServices.existsByEmail(userApp.getEmail());

        // Create new user's account
        UserApp user = userServices.saveUser(userApp);

        //Creating a token from UUID
        String token = UUID.randomUUID().toString();

        //Getting the confirmation token and then saving it
        confirmationTokenService.saveConfirmationToken(user, token);

        //Returning token
        return token;
    }


}
