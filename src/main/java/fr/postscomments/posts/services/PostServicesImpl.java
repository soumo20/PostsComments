package fr.postscomments.posts.services;

import fr.postscomments.shared.EntityNotFoundException;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServicesImpl implements PostServices {
    private final PostRepository postsRepository;

    public PostServicesImpl(PostRepository postsRepository) {
        this.postsRepository = postsRepository;
    }


    @Override
    public Post addPost(Post post) {
        return postsRepository.save(post);
    }

    @Override
    public List<Post> findAllPosts() {
        return postsRepository.findAll();
    }

    @Override
    public Post findPostById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The id is null");
        }
        return postsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with the given id " + id));
    }

    @Override
    public void deletePost(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The id is null");
        }
        postsRepository.deleteById(id);
    }

    @Override
    public Post updatePost(Post post) {
        return postsRepository.save(post);
    }


}
