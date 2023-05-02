package fr.postsComments.comments.services;

import fr.postsComments.comments.models.Comment;
import fr.postsComments.posts.models.Post;

import java.util.List;
import java.util.Optional;

public interface ICommentServices {
    List<Comment> findAllComment();

    Comment  findCommentById(Long id);
    Comment addComment(Comment commentToAdd);

    Comment updateComment(Comment commentUpdated);

    void deleteComment(Long id);

}
