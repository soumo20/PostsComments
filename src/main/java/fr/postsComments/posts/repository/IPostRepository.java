package fr.postsComments.posts.repository;

import fr.postsComments.posts.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
}
