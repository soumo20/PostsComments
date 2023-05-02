package fr.postsComments.posts.services;

import fr.postsComments.posts.models.Post;

import java.util.List;
import java.util.Optional;

public interface IPostServices {

    Post addPost(Post post);

    List<Post> findAll();

    Post findOneById(Long id);

    void delete(Long id);

    Post updatePost(Post post);


}
