package fr.postscomments.authentification.repository;

import fr.postscomments.authentification.models.UserApp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepository userRepository;


    @Nested
    @DisplayName("Verify if the email exist")
    class CheckExistByEmail {
        @Test
        void existByEmailTrue() {
            //Given
            String email = "existByEmail@gmail.com";
            UserApp user = UserApp.builder().email(email).passeword("0000").phone("0606060606").build();
            userRepository.save(user);

            //When
            Boolean existByEmail = userRepository.existByEmail(email);

            //Then
            assertTrue(existByEmail);
        }

        @Test
        void existByEmailFalse() {
            //Given
            String email = "donExistByEmail@gmail.com";

            //When
            Boolean existByEmail = userRepository.existByEmail(email);

            //Then
            assertFalse(existByEmail);
        }
    }

}