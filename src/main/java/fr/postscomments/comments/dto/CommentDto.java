package fr.postscomments.comments.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentDto {

    @NotBlank(message = "The comment must not be blanck")
    private String content;
}
