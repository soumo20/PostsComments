package fr.postscomments.comments.repository;

import fr.postscomments.comments.models.Comment;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.IPostRepository;
import fr.postscomments.posts.services.IPostServices;
import fr.postscomments.posts.services.PostServicesImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ICommentRepositoryTest {


    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IPostServices postServices;
    private Post postSaved1 ;

    @BeforeEach
    public void setup() {
        // create and save some posts
        Post post1 = Post.builder()
                .title("Post 1")
                .contente("This is the content of Post 1")
                .build();
       postSaved1 = postServices.addPost(post1);
    }


    @Test
    void findAllCommentsOfOnePost() {
        //Given
        Comment comment1 = Comment.builder()
                .contente("This is a comment on Post 1")
                .post(postSaved1)
                .build();

        Comment comment2 = Comment.builder()
                .contente("This is another comment on Post 1")
                .post(postSaved1)
                .build();

        Comment comment3 = Comment.builder()
                .contente("This is a comment on Post 2")
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