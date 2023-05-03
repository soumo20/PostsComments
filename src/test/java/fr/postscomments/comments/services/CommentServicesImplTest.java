package fr.postscomments.comments.services;

import fr.postscomments.comments.models.Comment;
import fr.postscomments.comments.repository.ICommentRepository;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.IPostRepository;
import fr.postscomments.shared.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServicesImplTest {
    @InjectMocks
    private CommentServicesImpl commentServices;

    @Mock
    private ICommentRepository commentRepository;

    @Mock
    private IPostRepository postRepository;

    @Test
    void addCommentToExistingPostIdNull() {
        //Given
        Comment commentToAdd = Comment.builder()
                .contente("This is a good post")
                .build();
        //when
        assertThatThrownBy(() -> commentServices.addComment(commentToAdd, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The id of the post must not be null");
        verify(commentRepository, never()).save(any());
    }

    @Test
    void addCommentPostFailledPostNotExist() {
        //Given
        Post postSaved = Post.builder().id(1L)
                .title("3 Tips for Staying Focused While Working From DESK")
                .contente("""
                        1.Establish a routine: Having a set routine can help you create structure in your day and keep 
                        you on track.
                        """)
                .build();
        Comment commentToAdd = Comment.builder()
                .contente("This is a good post")
                .post(postSaved)
                .build();

        //Then
        assertThatThrownBy(() -> commentServices.addComment(commentToAdd, postSaved.getId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("No post founded with the given id");
        verify(commentRepository, never()).save(any());
    }

    @Test
    void addCommentToExistingPostSuccess() {
        //Given
        Post postSaved = Post.builder().id(1L)
                .title("3 Tips for Staying Focused While Working From DESK")
                .contente("""
                        1.Establish a routine: Having a set routine can help you create structure in your day and keep 
                        you on track.
                        """)
                .build();
        Comment commentToAdd = Comment.builder()
                .contente("This is a good post")
                .post(postSaved)
                .build();

        //When
        when(postRepository.findById(postSaved.getId())).thenReturn(Optional.of(postSaved));
        Comment commentAdded = commentServices.addComment(commentToAdd, postSaved.getId());
        //Then
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();
        assertThat(capturedComment).isEqualTo(commentToAdd);
    }

}
