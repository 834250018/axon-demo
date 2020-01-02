package com.ywy.learn.query.repository;

import com.ywy.learn.query.entry.ArticleEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author ve
 * @date 2019/3/29 9:11
 */
public interface ArticleEntryRepository extends MongoRepository<ArticleEntry, String>, QueryDslPredicateExecutor<ArticleEntry> {
    }
