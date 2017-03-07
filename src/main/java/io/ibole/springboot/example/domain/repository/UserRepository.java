package io.ibole.springboot.example.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import io.ibole.springboot.example.domain.User;

public interface UserRepository extends MongoRepository<User, Long>  {
	
	/**
    *
    * @param criteria
    * @param pageable
    * @return Page<User>
    */
   Page<User> find(Criteria criteria, Pageable pageable);

}
