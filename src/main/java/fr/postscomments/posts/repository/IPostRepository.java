package fr.postscomments.posts.repository;

import fr.postscomments.posts.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
}
