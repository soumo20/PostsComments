package fr.postscomments.comments.repository;

import fr.postscomments.comments.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

     @Query("SELECT c FROM Comment c WHERE c.post.id = :idPost")
    List<Comment> findAllCommentsOfOnePost(@Param("idPost") Long idPost);
}

