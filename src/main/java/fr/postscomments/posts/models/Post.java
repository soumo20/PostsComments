package fr.postscomments.posts.models;

import fr.postscomments.authentification.models.UserApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    private Long id;

    @NotBlank(message = "The title must not be blank")
    private String title;

    @NotBlank(message = "The type must not be blanck")
    @Column(name = "content_post")
    private String contente;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserApp author;

}
