package fr.postscomments.comments.services;

import fr.postscomments.comments.dto.CommentDto;
import fr.postscomments.comments.models.Comment;

import java.util.List;

public interface CommentServices {

    Comment findCommentById(Long id);

    Comment addComment(CommentDto commentDto, Long idPost);

    Comment updateComment(Comment commentUpdated);

    void deleteComment(Long id);

    List<Comment> findAllCommentsOfOnePost(Long idPost);

}
