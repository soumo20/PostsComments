package fr.postscomments.posts.repository;

import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.services.IPostServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IPostRepositoryTest {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IPostServices postServices;

    private Post postSaved1;

    @BeforeEach
    public void setUp() {
        Post post1 = Post.builder()
                .title("Post 1")
                .content("This is the content of Post 1")
                .build();
        postSaved1 = postServices.addPost(post1);
    }

    @Test
    void existPostByIdTrue() {
        //When
        Boolean existPost = postRepository.existPostById(postSaved1.getId());

        //Then
        assertTrue(existPost);
    }


    @Test
    void existPostByIdfALSE() {
        //When
        Boolean existPost = postRepository.existPostById(5L);

        //Then
        assertFalse(existPost);
    }
}