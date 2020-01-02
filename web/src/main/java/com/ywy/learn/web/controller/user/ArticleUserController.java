package com.ywy.learn.web.controller.user;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.article.api.command.ArticleCommentCommand;
import com.ywy.learn.command.article.api.command.ArticleUpdateCommand;
import com.ywy.learn.query.entry.ArticleEntry;
import com.ywy.learn.query.repository.ArticleEntryRepository;
import com.ywy.learn.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.axonframework.messaging.MetaData;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */

@Api(value = "ArticleUserController", tags = "前台")
@Validated
@RequestMapping(value = "/user/Article")
@RestController
public class ArticleUserController extends BaseController {

    @Autowired
    ArticleEntryRepository ArticleEntryRepository;

    @ApiOperation(value = "查询单篇文章")
    @ApiParam
    @GetMapping(value = "/one")
    public ArticleEntry one(@NotBlank @RequestParam(value = "id") String id) {
        return ArticleEntryRepository.findOne(id);
    }

    @ApiOperation(value = "查询文章列表")
    @GetMapping(value = "/list")
    public Iterable<ArticleEntry> list(@QuerydslPredicate(root = ArticleEntry.class) Predicate predicate) {
        return ArticleEntryRepository.findAll(predicate);
    }

    @ApiOperation(value = "查询文章分页")
    @GetMapping(value = "/page")
    public Page<ArticleEntry> page(@QuerydslPredicate(root = ArticleEntry.class) Predicate predicate, Pageable pageable) {
        return ArticleEntryRepository.findAll(predicate, pageable);
    }

    @ApiOperation(value = "评论")
    @PostMapping(value = "/comment")
    public void create(@RequestBody @Valid ArticleCommentCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
    }
}
