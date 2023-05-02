package fr.postsComments.posts.services;

import fr.postsComments.Shared.EntityNotFoundException;
import fr.postsComments.posts.models.Post;
import fr.postsComments.posts.repository.IPostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServicesImplTest {

    @InjectMocks
    private PostServicesImpl postServices;

    @Mock
    private IPostRepository postRepository;

    Post postToSave1 = Post.builder()
            .title("3 Tips for Staying Focused While Working From Home")
            .contente("""
                    1.Establish a routine: Having a set routine can help you create structure in your day and keep 
                    you on track.
                    2. Create a designated workspace:Set up a space that is solely dedicated to work.
                    3.Minimize distractions:Identify what distracts you and eliminate as many of those distractions 
                    as possible.""")
            .build();
    Post postToSave2 = Post.builder()
            .title("3 Tips for Staying Focused While Working From DESK")
            .contente("""
                    1.Establish a routine: Having a set routine can help you create structure in your day and keep 
                    you on track.
                    """)
            .build();
    Post postWithId = Post.builder().id(1L)
            .title("3 Tips for Staying Focused While Working From DESK")
            .contente("""
                    1.Establish a routine: Having a set routine can help you create structure in your day and keep 
                    you on track.
                    """)
            .build();

    @Test
    void savePostWithSuccess() {

        //When
        Post postSaved = postServices.addPost(postToSave1);

        //Then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();
        assertThat(capturedPost).isEqualTo(postToSave1);
    }


    @Test
    void findAllCallTheRightMethodFromRepository() {

        //When
        List<Post> listPostsFounded = postServices.findAllPosts();

        //Then
        verify(postRepository).findAll();
    }

    @Test
    void findPostByIdNullThrowException() {
        assertThatThrownBy(() -> postServices.findPostById(postToSave1.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The id is null");
        verify(postRepository, never()).findById(any());
    }

    @Test
    void findByIdThrowEntityNotFournd() {
        assertThatThrownBy(() -> postServices.findPostById(postWithId.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Post not found with the given id " + postWithId.getId());
    }

}