package com.ywy.learn.query.repository;

import com.ywy.learn.query.entry.CommentEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author ve
 * @date 2019/3/29 9:11
 */
public interface CommentEntryRepository extends MongoRepository<CommentEntry, String>, QueryDslPredicateExecutor<CommentEntry> {
}
