package fr.postscomments.authentification.register;

import fr.postscomments.authentification.models.ERole;
import fr.postscomments.authentification.models.Role;
import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.RoleRepository;
import fr.postscomments.authentification.repository.UserRepository;
import fr.postscomments.authentification.validationmail.email.EmailSender;
import fr.postscomments.authentification.validationmail.email.EmailValidator;
import fr.postscomments.authentification.validationmail.token.ConfirmationToken;
import fr.postscomments.authentification.validationmail.token.ConfirmationTokenService;
import fr.postscomments.shared.exceptions.EntityAlreadyExist;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmTokenService;
    private final EmailSender emailSender;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final ConfirmationTokenService confirmationTokenService;


    private static final String ERROR_ROLE_NOT_FOUND = "Error: Role is not found.";

    public RegistrationService(UserRepository userRepository, EmailValidator emailValidator, ConfirmationTokenService confirmTokenService, EmailSender emailSender, RoleRepository roleRepository, PasswordEncoder encoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.confirmTokenService = confirmTokenService;
        this.emailSender = emailSender;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String register(SignUpRequest request) {

        if (!emailValidator.test(request.getEmail())) {
            throw new IllegalStateException(String.format("Email %s, not valid", request.getEmail()));
        }
        String tokenForNewUser = registreUserService(new UserApp(request.getEmail(), request.getPassword(), request.getPhone(), request.getRoles()));

        //Since, we are running the spring boot application in localhost, we are hardcoding the
        //url of the server. We are creating a POST request with token param
        String link = "http://localhost:8080/api/auth/registration/confirm/token=" + tokenForNewUser;
        emailSender.sendEmail(request.getEmail(), buildEmail(request.getEmail(), link));
        return tokenForNewUser;

    }

    public String registreUserService(UserApp userApp) {

        if (userRepository.existsByEmail(userApp.getEmail())) {
            throw new EntityAlreadyExist("user already exist");
        }

        Set<Role> roles = modificationRoles(userApp);

        // Create new user's account
        UserApp user = new UserApp(userApp.getEmail()
                , encoder.encode(userApp.getPassword()), userApp.getPhone(), roles);

        userRepository.save(user);

        //Creating a token from UUID
        String token = UUID.randomUUID().toString();

        //Getting the confirmation token and then saving it
        saveConfirmationToken(user, token);

        //Returning token
        return token;
    }

    public Set<Role> modificationRoles(UserApp userApp) {
        Set<Role> strRoles = userApp.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findOneByNameRole(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findOneByNameRole(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findOneByNameRole(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
                    roles.add(userRole);
                }
            });
        }
        return roles;
    }

    @Transactional
    public String confirmToken(String token) {
        Optional<ConfirmationToken> confirmToken = confirmTokenService.getToken(token);

        if (confirmToken.isEmpty()) {
            throw new IllegalStateException("Token not found!");
        }

        if (confirmToken.get().getConfirmedAt() != null) {
            throw new IllegalStateException("Email is already confirmed");
        }

        LocalDateTime expiresAt = confirmToken.get().getExpiresAt();

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is already expired!");
        }

        confirmTokenService.setConfirmedAt(token);
        this.enableAppUser(confirmToken.get().getUserApp().getEmail());

        //Returning confirmation message if the token matches
        return "Your email is confirmed. Thank you for using our service!";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    private void saveConfirmationToken(UserApp userApp, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), userApp);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }


    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);

    }
}
