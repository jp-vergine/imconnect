package com.imconnect.core.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.fakemongo.junit.FongoRule;
import com.imconnect.core.config.DefaultConfig;
import com.imconnect.core.model.user.User;
import com.imconnect.core.repositories.user.UserRepository;
import com.imconnect.core.repositories.util.SequenceId;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DefaultConfig.class }, loader = SpringApplicationContextLoader.class)
public class UsersRepositoryTest {

	@Value("${mongo.url}")
	private String mongoUrl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Rule
	public FongoRule fongoRule = new FongoRule("demo-test", false);

	@Test
	public void test_nom_database_ok() {
		Assert.assertTrue("mongodb://127.0.0.1:27017".equals(mongoUrl));
	}

	@Test
	// @UsingDataSet(locations = {"/two-users.json"}, loadStrategy =
	// LoadStrategyEnum.CLEAN_INSERT)
	public void test_nb_user_after_insert_ok() {

		userRepository.deleteAll();
		
		assertEquals(0, userRepository.count());
		
		SequenceId id = new SequenceId();
		id.setId("user");
		id.setSeq(0);
		mongoTemplate.save(id);

		User foo = new User();
		foo.setPseudo("Foo 1");
		userRepository.save(foo);

		assertEquals(1, userRepository.countAllUsers().intValue());
	}

	@Test
	public void test_generation_sequence_ok() {

		userRepository.deleteAll();
		
		SequenceId id = new SequenceId();
		id.setId("user");
		id.setSeq(0);
		mongoTemplate.save(id);

		User foo = new User();
		foo.setPseudo("Foo 1");
		userRepository.save(foo);

		User foo2 = new User();
		foo2.setPseudo("Foo 2");
		userRepository.save(foo2);

		assertEquals(foo2.getId().intValue(), foo.getId() + 1);
	}
}
