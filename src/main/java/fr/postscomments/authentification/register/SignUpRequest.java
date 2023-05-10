package fr.postscomments.authentification.register;

import fr.postscomments.authentification.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "The email must not be blank")
    @Email(message = "the email must be in valid format")
    private String email;

    @NotBlank(message = "The password must not be blank")
    private String password;


    @NotBlank(message = "The phone number must not be blank")
    @Pattern(regexp = "(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}", message = "Please enter a valid format of the phone number")
    private String phone;

    private Set<Role> roles;

}
