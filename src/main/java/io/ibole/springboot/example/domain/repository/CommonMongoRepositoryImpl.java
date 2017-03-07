/**
 * 
 */
package io.ibole.springboot.example.domain.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

/**
 * @author zijia.wang
 *
 */
public class CommonMongoRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID>
		implements CommonRepository<T, ID> {

	protected final MongoOperations mongoTemplate;

	protected final MongoEntityInformation<T, ID> entityInformation;

	public CommonMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);

		this.mongoTemplate = mongoOperations;
		this.entityInformation = metadata;
	}

	protected Class<T> getEntityClass() {
		return entityInformation.getJavaType();
	}

	@Override
	public Page<T> find(Query query, Pageable p) {
		long total = mongoTemplate.count(query, getEntityClass());
		List<T> list = mongoTemplate.find(query.with(p), getEntityClass());
		return new PageImpl<T>(list, p, total);
	}

	@Override
	public Page<T> find(Criteria criteria, Pageable p) {
		return find(new Query(criteria), p);
	}

}
