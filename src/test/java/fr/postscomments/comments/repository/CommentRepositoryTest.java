package fr.postscomments.comments.repository;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.comments.models.Comment;
import fr.postscomments.posts.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;


    private Post postSaved1;


    @Test
    void findAllCommentsOfOnePost() {
        //Given
        UserApp author = new UserApp();
        entityManager.persistAndFlush(author);

        Post post = new Post();
        post.setTitle("Test post");
        post.setContent("Test content");
        post.setAuthor(author);
        entityManager.persistAndFlush(post);

        Comment comment1 = Comment.builder()
                .content("This is a comment on Post 1")
                .post(post)
                .build();

        Comment comment2 = Comment.builder()
                .content("This is another comment on Post 1")
                .post(post)
                .build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        List<Comment> listOfCommentExpected = new ArrayList<>();
        listOfCommentExpected.add(comment1);
        listOfCommentExpected.add(comment2);
        //when
        List<Comment> listOfCommentFounded = commentRepository.findAllCommentsOfOnePost(1L);

        //Then
        assertNotNull(listOfCommentFounded);
        assertIterableEquals(listOfCommentExpected, listOfCommentFounded);

    }
}