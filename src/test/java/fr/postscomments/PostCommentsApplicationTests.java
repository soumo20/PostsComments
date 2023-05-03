package fr.postscomments;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
class PostCommentsApplicationTests {

	private final ApplicationContext applicationContext;

	PostCommentsApplicationTests(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
	}

}
