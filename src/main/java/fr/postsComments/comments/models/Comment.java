package fr.postsComments.comments.models;

import fr.postsComments.authentification.models.UserApp;
import fr.postsComments.posts.models.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long id;

    @Column(name = "Contente_comment")
    @NotBlank(message = "The comment must not be blanck")
    private String contente;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserApp author;

    @ManyToOne
    @JoinColumn(name = "id_post")
    @NotNull
    private Post post;


}
