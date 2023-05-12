package fr.postscomments.authentification.validationmail.token;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.security.services.user.UserServices;
import fr.postscomments.shared.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final UserServices userServices;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, UserServices userServices) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userServices = userServices;
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Token not found!"));
    }

    @Transactional
    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmToken = getToken(token);


        if (confirmToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email is already confirmed");
        }

        LocalDateTime expiresAt = confirmToken.getExpiresAt();

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is already expired!");
        }

        confirmToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmToken);

        userServices.enableAppUser(confirmToken.getUserApp().getEmail());

        //Returning confirmation message if the token matches
        return "Your email is confirmed. Thank you for using our service!";
    }

    @Override
    public void saveConfirmationToken(UserApp userApp, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), userApp);
        saveConfirmationToken(confirmationToken);
    }
}
