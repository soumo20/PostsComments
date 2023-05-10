package fr.postscomments.posts.services;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.security.services.userServices.UserServices;
import fr.postscomments.posts.dto.PostDto;
import fr.postscomments.shared.exceptions.EntityNotFoundException;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServicesImplTest {

    @InjectMocks
    private PostServicesImpl postServices;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserServices userServices;

    PostDto postToSave1 = PostDto.builder()
            .title("3 Tips for Staying Focused While Working From Home")
            .content("""
                    1.Establish a routine: Having a set routine can help you create structure in your day and keep
                    you on track.
                    2. Create a designated workspace:Set up a space that is solely dedicated to work.
                    3.Minimize distractions:Identify what distracts you and eliminate as many of those distractions
                    as possible.""")
            .build();
    Post postToSave2 = Post.builder()
            .title("3 Tips for Staying Focused While Working From DESK")
            .content("""
                    1.Establish a routine: Having a set routine can help you create structure in your day and keep
                    you on track.
                    """)
            .build();
    Post postWithId = Post.builder().id(1L)
            .title("3 Tips for Staying Focused While Working From DESK")
            .content("""
                    1.Establish a routine: Having a set routine can help you create structure in your day and keep
                    you on track.
                    """)
            .build();

    @Test
    void savePostWithSuccess() {
        // Mock the userServices.findUserConnected() method
        UserApp user = new UserApp();
        when(userServices.findUserConnected()).thenReturn(user);

        //When
        Post postSaved = Post.builder()
                .id(1L)
                .title("test add post")
                .content("This is a new post for test")
                .author(user).build();

        when(postRepository.save(any(Post.class))).thenReturn(postSaved);
        Post result = postServices.addPost(postToSave1);

        //Then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());

        Post capturedPost = postArgumentCaptor.getValue();
        assertThat(capturedPost.getContent()).isEqualTo(postToSave1.getContent());
        assertThat(capturedPost.getTitle()).isEqualTo(postToSave1.getTitle());
    }


    @Test
    void findAllCallTheRightMethodFromRepository() {
        //given
        List<Post> posts = List.of(postToSave2);
        //When
        when(postRepository.findAll()).thenReturn(posts);
        List<Post> listPostsFounded = postServices.findAllPosts();

        //Then
        verify(postRepository).findAll();
        assertThat(listPostsFounded).isEqualTo(posts);
    }

    @Test
    void findPostByIdNullThrowException() {
        assertThatThrownBy(() -> postServices.findPostById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The id is null");
        verify(postRepository, never()).findById(any());
    }

    @Test
    void findByIdThrowEntityNotFournd() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> postServices.findPostById(anyLong()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findByIdSuccessful() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(postWithId));
        Post postFounded = postServices.findPostById(postWithId.getId());

        assertThat(postFounded).isNotNull();

    }

}