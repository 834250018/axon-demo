package com.ywy.learn.web.controller;

import com.ywy.command.user.api.command.UserRemoveCommand;
import com.ywy.command.user.api.command.UserUpdateCommand;
import com.ywy.learn.infrastructure.gateway.MetaDataGateway;
import com.ywy.command.user.api.command.UserCreateCommand;
import com.ywy.learn.query.entry.UserEntry;
import com.ywy.learn.query.repository.UserEntryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.axonframework.messaging.MetaData;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */

@Api(value = "UserController", tags = "用户")
@Validated
@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    MetaDataGateway metaDataGateway;

    @Autowired
    UserEntryRepository userEntryRepository;

    @ApiOperation(value = "查询单个用户")
    @ApiParam
    @GetMapping(value = "/one")
    public UserEntry one(@NotBlank @RequestParam(value = "userId") String userId) {
        return userEntryRepository.findOne(userId);
    }

    @ApiOperation(value = "用户列表")
    @GetMapping(value = "/list")
    public List<UserEntry> list() {
        return userEntryRepository.findAll();
    }

    @ApiOperation(value = "用户分页")
    @GetMapping(value = "/page")
    public Page<UserEntry> page(Pageable pageable) {
        return userEntryRepository.findAll(pageable);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/create")
    public void create(@RequestBody UserCreateCommand command) {
        try {
            metaDataGateway.sendAndWait(command, MetaData.emptyInstance());
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/update")
    public void update(@RequestBody UserUpdateCommand command) {
        try {
            metaDataGateway.sendAndWait(command, MetaData.emptyInstance());
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/remove")
    public void delete(@RequestBody UserRemoveCommand command) {
        try {
            metaDataGateway.sendAndWait(command, MetaData.emptyInstance());
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
