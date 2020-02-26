package com.ywy.learn.web.controller.admin;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.article.api.command.ArticleCreateCommand;
import com.ywy.learn.command.article.api.command.ArticleRemoveCommand;
import com.ywy.learn.command.article.api.command.ArticleUpdateCommand;
import com.ywy.learn.query.entry.ArticleEntry;
import com.ywy.learn.query.repository.ArticleEntryRepository;
import com.ywy.learn.web.controller.base.BaseAdminController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(value = "ArticleBaseAdminController", tags = "后台")
@Validated
@RequestMapping(value = "/admin/article")
@RestController
public class ArticleAdminController extends BaseAdminController {

    @Autowired
    ArticleEntryRepository articleEntryRepository;

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

    @ApiOperation(value = "新增文章")
    @PostMapping(value = "/create")
    public void create(@RequestBody @Valid ArticleCreateCommand command) {
        sendAndWait(command);
    }

    @ApiOperation(value = "修改文章")
    @PutMapping(value = "/update")
    public void update(@RequestBody @Valid ArticleUpdateCommand command) {
        sendAndWait(command);
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping(value = "/remove")
    public void delete(@RequestBody @Valid ArticleRemoveCommand command) {
        sendAndWait(command);
    }
}
