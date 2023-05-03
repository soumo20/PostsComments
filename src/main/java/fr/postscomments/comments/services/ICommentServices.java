package fr.postscomments.comments.services;

import fr.postscomments.comments.models.Comment;

import java.util.List;

public interface ICommentServices {

    Comment findCommentById(Long id);

    Comment addComment(Comment commentToAdd, Long idPost);

    Comment updateComment(Comment commentUpdated);

    void deleteComment(Long id);

    List<Comment> findAllCommentsOfOnePost(Long idPost);

}
