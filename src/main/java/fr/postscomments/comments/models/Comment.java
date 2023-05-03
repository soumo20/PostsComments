package fr.postscomments.comments.models;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.posts.models.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
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
