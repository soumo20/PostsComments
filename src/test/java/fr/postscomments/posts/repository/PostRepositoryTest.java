package fr.postscomments.posts.repository;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.posts.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    void testExistPostByIdTrue() {
        UserApp author = new UserApp();
        entityManager.persistAndFlush(author);

        Post post = new Post();
        post.setTitle("Test post");
        post.setContent("Test content");
        post.setAuthor(author);
        entityManager.persistAndFlush(post);

        boolean result = postRepository.existPostById(post.getId());

        assertTrue(result);
    }

    @Test
    void existPostByIdfALSE() {
        //When
        Boolean existPost = postRepository.existPostById(5L);

        //Then
        assertFalse(existPost);
    }
}