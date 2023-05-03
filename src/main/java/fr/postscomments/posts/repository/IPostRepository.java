package fr.postscomments.posts.repository;

import fr.postscomments.posts.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
}
