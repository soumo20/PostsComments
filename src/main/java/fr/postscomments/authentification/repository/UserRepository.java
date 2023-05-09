package fr.postscomments.authentification.repository;

import fr.postscomments.authentification.models.UserApp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserApp u WHERE u.email = :email")
    Boolean existByEmail(@Param("email") String email);


    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserApp a SET a.enabled=true WHERE a.email=?1")
    int enableAppUser(String email);

}
