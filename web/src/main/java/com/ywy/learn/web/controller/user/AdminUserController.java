package com.ywy.learn.web.controller.user;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.admin.api.command.AdminCreateCommand;
import com.ywy.learn.command.admin.api.command.AdminRemoveCommand;
import com.ywy.learn.command.admin.api.command.AdminUpdateCommand;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.web.controller.base.BaseController;
import com.ywy.learn.web.controller.base.UserController;
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

@Api(value = "AdminUserController", tags = "前台")
@Validated
@RequestMapping(value = "/user/Admin")
@RestController
public class AdminUserController extends UserController {

    @Autowired
    AdminEntryRepository AdminEntryRepository;

    @ApiOperation(value = "查询单篇文章")
    @ApiParam
    @GetMapping("/one")
    public AdminEntry one(@NotBlank @RequestParam(value = "id") String id) {
        return AdminEntryRepository.findOne(id);
    }

    @ApiOperation(value = "查询文章列表")
    @GetMapping("/list")
    public Iterable
            <AdminEntry> list(@QuerydslPredicate(root = AdminEntry.class) Predicate predicate) {
        return AdminEntryRepository.findAll(predicate);
    }

    @ApiOperation(value = "查询文章分页")
    @GetMapping("/page")
    public Page
            <AdminEntry> page(@QuerydslPredicate(root = AdminEntry.class) Predicate predicate,
                              Pageable pageable) {
        return AdminEntryRepository.findAll(predicate, pageable);
    }

    @ApiOperation(value = "新增文章")
    @PostMapping(value = "/create")
    public void create(@RequestBody @Valid AdminCreateCommand command) {
        sendAndWait(command);
    }

    @ApiOperation(value = "修改文章")
    @PutMapping(value = "/update")
    public void update(@RequestBody @Valid AdminUpdateCommand command) {
        sendAndWait(command);
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping(value = "/remove")
    public void delete(@RequestBody @Valid AdminRemoveCommand command) {
        sendAndWait(command);
    }
}
