package com.ywy.learn.web.controller.user;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.article.api.command.ArticleCommentCommand;
import com.ywy.learn.query.entry.ArticleEntry;
import com.ywy.learn.query.entry.CommentEntry;
import com.ywy.learn.query.repository.ArticleEntryRepository;
import com.ywy.learn.query.repository.CommentEntryRepository;
import com.ywy.learn.web.controller.base.BaseUserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.axonframework.common.IdentifierFactory;
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

@Api(value = "ArticleBaseUserController", tags = "前台")
@Validated
@RequestMapping(value = "/user/Article")
@RestController
public class ArticleUserController extends BaseUserController {

    @Autowired
    ArticleEntryRepository articleEntryRepository;

    @Autowired
    CommentEntryRepository commentEntryRepository;

    @ApiOperation(value = "查询单篇文章")
    @ApiParam
    @GetMapping("/one")
    public ArticleEntry one(@NotBlank @RequestParam(value = "id") String id) {
        return articleEntryRepository.findOne(id);
    }

    @ApiOperation(value = "查询文章列表")
    @GetMapping("/list")
    public Iterable<ArticleEntry> list(@QuerydslPredicate(root = ArticleEntry.class) Predicate predicate) {
        return articleEntryRepository.findAll(predicate);
    }

    @ApiOperation(value = "查询文章分页")
    @GetMapping("/page")
    public Page<ArticleEntry> page(@QuerydslPredicate(root = ArticleEntry.class) Predicate predicate, Pageable pageable) {
        return articleEntryRepository.findAll(predicate, pageable);
    }

    @ApiOperation(value = "评论")
    @PostMapping(value = "/comment")
    public void create(@RequestBody @Valid ArticleCommentCommand command) {
        command.setCommentId(IdentifierFactory.getInstance().generateIdentifier());
        commentEntryRepository.save(new CommentEntry(command.getCommentId(), "", command.getArticleId(), command.getContent(), System.currentTimeMillis()));
        sendAndWait(command);
    }
}
