package com.ywy.learn.web.controller.admin;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.user.api.command.UserCreateCommand;
import com.ywy.learn.command.user.api.command.UserRemoveCommand;
import com.ywy.learn.command.user.api.command.UserUpdateCommand;
import com.ywy.learn.query.entry.UserEntry;
import com.ywy.learn.query.repository.UserEntryRepository;
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

@Api(value = "ArticleAdminController", tags = "后台")
@Validated
@RequestMapping(value = "/admin/article")
@RestController
public class ArticleAdminController extends BaseController {

    @ApiOperation(value = "查询单篇文章")
    @ApiParam
    @GetMapping(value = "/one")
    public UserEntry one(@NotBlank @RequestParam(value = "userId") String userId) {
        return null;
    }

    @ApiOperation(value = "查询文章列表")
    @GetMapping(value = "/list")
    public Iterable<UserEntry> list(@QuerydslPredicate(root = UserEntry.class) Predicate predicate) {
        return null;
    }

    @ApiOperation(value = "查询文章分页")
    @GetMapping(value = "/page")
    public Page<UserEntry> page(@QuerydslPredicate(root = UserEntry.class) Predicate predicate, Pageable pageable) {
        return null;
    }

    @ApiOperation(value = "新增文章")
    @PostMapping(value = "/create")
    public void create(@RequestBody @Valid UserCreateCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
    }

    @ApiOperation(value = "修改文章")
    @PutMapping(value = "/update")
    public void update(@RequestBody @Valid UserUpdateCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping(value = "/remove")
    public void delete(@RequestBody @Valid UserRemoveCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
    }
}
