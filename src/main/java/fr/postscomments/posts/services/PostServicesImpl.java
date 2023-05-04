package fr.postscomments.posts.services;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.security.services.UserServices;
import fr.postscomments.posts.dto.PostDto;
import fr.postscomments.shared.EntityNotFoundException;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServicesImpl implements PostServices {
    private final PostRepository postsRepository;

    private final UserServices userServices;

    public PostServicesImpl(PostRepository postsRepository, UserServices userServices) {
        this.postsRepository = postsRepository;
        this.userServices = userServices;
    }


    @Override
    public Post addPost(PostDto postDto) {
        UserApp user = userServices.findUserConnected();
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(user)
                .build();

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
        post.setAuthor(userServices.findUserConnected());
        return postsRepository.save(post);
    }
}
