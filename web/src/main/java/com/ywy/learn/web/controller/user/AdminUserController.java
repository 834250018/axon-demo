package com.ywy.learn.web.controller.user;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.admin.api.command.AdminCreateCommand;
import com.ywy.learn.command.admin.api.command.AdminRemoveCommand;
import com.ywy.learn.command.admin.api.command.AdminUpdateCommand;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.web.controller.base.BaseUserController;
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

@Api(value = "AdminUserController", tags = "前台")
@Validated
@RequestMapping(value = "/user/Admin")
@RestController
public class AdminUserController extends BaseUserController {

    @Autowired
    AdminEntryRepository AdminEntryRepository;

    @ApiOperation(value = "admin-one")
    @ApiParam
    @GetMapping("/one")
    public AdminEntry one(@NotBlank @RequestParam(value = "id") String id) {
        return AdminEntryRepository.findOne(id);
    }

    @ApiOperation(value = "admin-list")
    @GetMapping("/list")
    public Iterable
            <AdminEntry> list(@QuerydslPredicate(root = AdminEntry.class) Predicate predicate) {
        return AdminEntryRepository.findAll(predicate);
    }

    @ApiOperation(value = "admin-page")
    @GetMapping("/page")
    public Page
            <AdminEntry> page(@QuerydslPredicate(root = AdminEntry.class) Predicate predicate,
                              Pageable pageable) {
        return AdminEntryRepository.findAll(predicate, pageable);
    }

    @ApiOperation(value = "admin-create")
    @PostMapping(value = "/create")
    public void create(@RequestBody @Valid AdminCreateCommand command) {
        sendAndWait(command);
    }

    @ApiOperation(value = "admin-update")
    @PutMapping(value = "/update")
    public void update(@RequestBody @Valid AdminUpdateCommand command) {
        sendAndWait(command);
    }

    @ApiOperation(value = "admin-delete")
    @DeleteMapping(value = "/remove")
    public void delete(@RequestBody @Valid AdminRemoveCommand command) {
        sendAndWait(command);
    }
}
