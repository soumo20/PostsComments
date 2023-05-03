package fr.postscomments.authentification.models;

import jakarta.persistence.*;
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

    private String email;

    private String passeword;

    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user-roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles;

    public UserApp(String email, String passeword, String phone, Set<Role> roles) {
        this.email = email;
        this.passeword = passeword;
        this.phone = phone;
        this.roles = roles;
    }
}
