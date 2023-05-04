package fr.postscomments.comments.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotBlank(message = "The comment must not be blanck")
    private String content;
}
