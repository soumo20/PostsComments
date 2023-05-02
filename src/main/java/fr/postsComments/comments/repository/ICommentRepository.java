package fr.postsComments.comments.repository;

import fr.postsComments.comments.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {

}
