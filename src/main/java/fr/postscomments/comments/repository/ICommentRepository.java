package fr.postscomments.comments.repository;

import fr.postscomments.comments.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {

}
