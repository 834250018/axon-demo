package com.ywy.learn.query.repository;

import com.ywy.learn.query.entry.AdminEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author ve
 * @date 2019/3/29 9:11
 */
public interface AdminEntryRepository extends MongoRepository
        <AdminEntry, String>, QueryDslPredicateExecutor
        <AdminEntry> {
}
