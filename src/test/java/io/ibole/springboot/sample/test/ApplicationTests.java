package io.ibole.springboot.sample.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.ibole.springboot.example.domain.Colleague;
import io.ibole.springboot.example.domain.Recognition;
import io.ibole.springboot.example.domain.User;
import io.ibole.springboot.example.domain.repository.ColleagueRepository;
import io.ibole.springboot.example.domain.repository.UserRepository;

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

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = {"io.ibole.springboot.example.domain.repository"})
public class ApplicationTests {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private ColleagueRepository colleagueRepo;
	
	final String[] names = new String[] { "张三", "李四" };

	protected final String password = "123456";

	protected Pageable pageable = new PageRequest(0, 10);

	protected static Logger logger = LoggerFactory.getLogger(ApplicationTests.class);
	
	//private MongodExecutable mongodExecutable = null;

	@Before
	public void setUp() throws UnknownHostException, IOException {
		//SpringApplication.run(ApplicationTests.class);
	  //startMongoDb();
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
		
		
		List<Recognition> recognitions = new ArrayList<Recognition>();
		recognitions.add(new Recognition("quote123"));
		Colleague colleague = new Colleague("name123", recognitions);
		colleagueRepo.save(colleague);
	}
	
	@Test
    public void select(){
        List<User> users=userRepo.findAll();
        Colleague colleague = (Colleague) colleagueRepo.findByName("name123");
        assertThat(colleague.recognitions.get(0).quote, is("quote123"));
        
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
        colleagueRepo.deleteAll();
        //stopMongoDb();
    }
    
//  private void startMongoDb() throws UnknownHostException, IOException {
//    MongodStarter starter = MongodStarter.getDefaultInstance();
//
//    String bindIp = "localhost";
//    int port = 27017;
//    IMongodConfig mongodConfig =
//        new MongodConfigBuilder().version(Version.Main.PRODUCTION)
//            .net(new Net(bindIp, port, Network.localhostIsIPv6())).build();
//
//    mongodExecutable = starter.prepare(mongodConfig);
//    MongodProcess mongod = mongodExecutable.start();
//
//    // MongoClient mongo = new MongoClient(bindIp, port);
//    // DB db = mongo.getDB("test");
//    // DBCollection col = db.createCollection("testCol", new BasicDBObject());
//    // col.save(new BasicDBObject("testDoc", new Date()));
//
//  }
//
//  private void stopMongoDb() {
//    if (mongodExecutable != null) {
//      mongodExecutable.stop();
//    }
//  }
}
