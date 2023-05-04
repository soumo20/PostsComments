package fr.postscomments.comments.repository;

import fr.postscomments.comments.models.Comment;
import fr.postscomments.posts.dto.PostDto;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.services.PostServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommentRepositoryTest {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostServices postServices;
    private Post postSaved1 ;

    @BeforeEach
    public void setup() {
        // create and save some posts
        PostDto post1 = PostDto.builder()
                .title("Post 1")
                .content("This is the content of Post 1")
                .build();
       postSaved1 = postServices.addPost(post1);
    }


    @Test
    void findAllCommentsOfOnePost() {
        //Given
        Comment comment1 = Comment.builder()
                .content("This is a comment on Post 1")
                .post(postSaved1)
                .build();

        Comment comment2 = Comment.builder()
                .content("This is another comment on Post 1")
                .post(postSaved1)
                .build();

        Comment comment3 = Comment.builder()
                .content("This is a comment on Post 2")
                .post(postSaved1)
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        List<Comment> listOfCommentExpected = new ArrayList<>();
        listOfCommentExpected.add(comment1);
        listOfCommentExpected.add(comment2);
        listOfCommentExpected.add(comment3);
        //when
        List<Comment> listOfCommentFounded = commentRepository.findAllCommentsOfOnePost(1L);

        //Then
        assertNotNull(listOfCommentFounded);
        assertIterableEquals(listOfCommentExpected,listOfCommentFounded);

    }
}