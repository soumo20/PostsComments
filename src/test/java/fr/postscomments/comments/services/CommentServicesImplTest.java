package fr.postscomments.comments.services;

import fr.postscomments.comments.models.Comment;
import fr.postscomments.comments.repository.ICommentRepository;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.IPostRepository;
import fr.postscomments.shared.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServicesImplTest {
    @InjectMocks
    private CommentServicesImpl commentServices;

    @Mock
    private ICommentRepository commentRepository;

    @Mock
    private IPostRepository postRepository;

    private static final Post POST_SAVED = Post.builder().id(1L)
            .title("3 Tips for Staying Focused While Working From DESK")
            .content("""
                    1.Establish a routine: Having a set routine can help you create structure in your day and keep
                    you on track.
                    """)
            .build();

    private static final Comment COMMENT_TO_ADD1 = Comment.builder()
            .content("This is a first comment")
            .post(POST_SAVED)
            .build();
    @Nested
    @DisplayName("AddCommentToPostTests")
    class AddCommentToPost {
        @Test
        void addCommentToExistingPostIdNull() {

            //when
            assertThatThrownBy(() -> commentServices.addComment(COMMENT_TO_ADD1, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The id of the post must not be null");
            verify(commentRepository, never()).save(any());
        }

        @Test
        void addCommentPostFailledPostNotExist() {


            //Then
            assertThatThrownBy(() -> commentServices.addComment(COMMENT_TO_ADD1, POST_SAVED.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("No post founded with the given id");
            verify(commentRepository, never()).save(any());
        }

        @Test
        void addCommentToExistingPostSuccess() {

            //When
            when(postRepository.findById(POST_SAVED.getId())).thenReturn(Optional.of(POST_SAVED));
            Comment commentAdded = commentServices.addComment(COMMENT_TO_ADD1, POST_SAVED.getId());
            //Then
            ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
            verify(commentRepository).save(commentArgumentCaptor.capture());
            Comment capturedComment = commentArgumentCaptor.getValue();
            assertThat(capturedComment).isEqualTo(COMMENT_TO_ADD1);
        }
    }

    @Nested
    @DisplayName("FoundCommentOfSpecPost")
    class FoundCommentOfPost {
        @Test
        void findAllCommentsOfOnePostidNull() {
            //when
            assertThatThrownBy(() -> commentServices.findAllCommentsOfOnePost(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The id of the post must not be null");
            verify(commentRepository, never()).findAllCommentsOfOnePost(any());
        }

        @Test
        void findAllCommentsOfOnePostidNotFound() {
            assertThatThrownBy(() -> commentServices.findAllCommentsOfOnePost(POST_SAVED.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("No post founded with the given id");
            verify(commentRepository, never()).findAllCommentsOfOnePost(any());
        }

        @Test
        void findAllCommentsOfOnePostidSucess() {
            //When
            when(postRepository.existPostById(POST_SAVED.getId())).thenReturn(true);
            List<Comment> listOfCommentFounded = commentServices.findAllCommentsOfOnePost(POST_SAVED.getId());
            //Then
            ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
            verify(commentRepository).findAllCommentsOfOnePost(idArgumentCaptor.capture());
            Long capturedID = idArgumentCaptor.getValue();
            assertThat(capturedID).isEqualTo(POST_SAVED.getId());
        }
    }


}
