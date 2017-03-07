package io.ibole.springboot.example.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public interface CommonRepository<T, ID> {

	public Page<T> find(Query query, Pageable p);

	public Page<T> find(Criteria criteria, Pageable p);
}
