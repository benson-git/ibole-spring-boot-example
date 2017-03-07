package io.ibole.springboot.example.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import io.ibole.springboot.example.domain.User;

@Repository
public class UserRepositoryImpl {
	
	@Autowired
	protected MongoTemplate mongoTemplate;

	/**
	 * @param criteria
	 * @param pageable
	 * @return
	 */
	Page<User> find(Criteria criteria, Pageable pageable) {
		Query query = new Query(criteria);
		final long total = mongoTemplate.count(query, User.class);
		final List<User> list = mongoTemplate.find(query.with(pageable), User.class);

		return new PageImpl<User>(list, pageable, total);
	}
}
