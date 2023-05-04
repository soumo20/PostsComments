package fr.postscomments.comments.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CommentDto {
    private String content;
}
