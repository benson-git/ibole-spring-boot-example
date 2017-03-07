package io.ibole.springboot.sample.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.ibole.springboot.example.domain.User;
import io.ibole.springboot.example.domain.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = {"io.ibole.springboot.example.domain.repository"})
public class ApplicationTests {

	@Autowired
	private UserRepository userRepo;
	
	final String[] names = new String[] { "张三", "李四" };

	protected final String password = "123456";

	protected Pageable pageable = new PageRequest(0, 10);

	protected static Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Before
	public void setUp() {
		//SpringApplication.run(ApplicationTests.class);
	}

	/**
	 * 插入数据
	 */
	@Before
	public void init() {
		inserTestData();
	}

	private void inserTestData() {

		for (String n : names) {
			User u = new User();
			u.setName(n).setPassword(password);
			userRepo.save(u);
			logger.info("插入用户：{}", u.toString());
		}
	}
	
	@Test
    public void select(){
        List<User> users=userRepo.findAll();
        assertThat(users.size(), is(2));

        assertThat(users.get(0).getPassword(), is(password));
        assertThat(users.get(0).getName(), is(names[0]));
    }

    @Test
    public void findByCriteria(){
        Page<User> page=userRepo.find(Criteria.where("password").is(password), pageable);

        assertThat(page.getTotalElements(), is(2L));

        page = userRepo.find(Criteria.where("name").is(names[0]),pageable);
        assertThat(page.getTotalElements(), is(1L));
        assertThat(page.getContent().get(0).getName(), is(names[0]));
    }

    @After
    public void clear(){
        logger.info("清空全部的测试数据......");
        userRepo.deleteAll();
    }
}
