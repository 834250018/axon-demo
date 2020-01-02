package com.ywy.learn.query.repository;

import com.ywy.learn.query.entry.${aggregate?cap_first}Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author ve
 * @date 2019/3/29 9:11
 */
public interface ${aggregate?cap_first}EntryRepository extends MongoRepository
<${aggregate?cap_first}Entry, String>, QueryDslPredicateExecutor
<${aggregate?cap_first}Entry> {
    }
