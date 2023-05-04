package fr.postscomments.posts.services;

import fr.postscomments.posts.dto.PostDto;
import fr.postscomments.posts.models.Post;

import java.util.List;

public interface PostServices {

    Post addPost(PostDto postDto);

    List<Post> findAllPosts();

    Post findPostById(Long id);

    void deletePost(Long id);

    Post updatePost(Post post);


}
