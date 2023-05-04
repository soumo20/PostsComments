package fr.postscomments.posts.repository;

import fr.postscomments.posts.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();

    @Query("" + "SELECT CASE WHEN COUNT(p) > 0 THEN " +
    "TRUE ELSE FALSE END "+
    "FROM Post p "+
    "WHERE p.id = :idPost")
    boolean existPostById(@Param("idPost") Long idPost);
}
