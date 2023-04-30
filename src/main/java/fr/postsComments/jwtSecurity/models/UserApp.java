package fr.postsComments.jwtSecurity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "user-app")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotBlank(message = "The username must not be blank")
    private String userName;

    @NotBlank(message = "The email must not be blank")
    @Email(message = "the email must be in valid format")
    private String email;

    @NotBlank(message="The password must not be blank")
    @Column(columnDefinition="TEXT")
    private String passeword;


    @NotBlank(message="The phone number must not be blank")
    @Pattern(regexp = "(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}", message="Please enter a valid format of the phone number")
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_roles",
    joinColumns = @JoinColumn(name = "id_user"),
             inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles;

    public UserApp(String userName, String email, String passeword, String phone, Set<Role> roles) {
        this.userName = userName;
        this.email = email;
        this.passeword = passeword;
        this.phone = phone;
        this.roles = roles;
    }
}
