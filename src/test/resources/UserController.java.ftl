package com.ywy.learn.web.controller.user;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.${aggregate}.api.command.${aggregate?cap_first}CreateCommand;
import com.ywy.learn.command.${aggregate}.api.command.${aggregate?cap_first}RemoveCommand;
import com.ywy.learn.command.${aggregate}.api.command.${aggregate?cap_first}UpdateCommand;
import com.ywy.learn.query.entry.${aggregate?cap_first}Entry;
import com.ywy.learn.query.repository.${aggregate?cap_first}EntryRepository;
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

@Api(value = "${aggregate?cap_first}UserController", tags = "前台")
@Validated
@RequestMapping(value = "/user/${aggregate?cap_first}")
@RestController
public class ${aggregate?cap_first}UserController extends BaseController {

    @Autowired
${aggregate?cap_first}EntryRepository ${aggregate?cap_first}EntryRepository;

    @ApiOperation(value = "查询单篇文章")
    @ApiParam
    @GetMapping(value = "/one")
    public ${aggregate?cap_first}Entry one(@NotBlank @RequestParam(value = "id") String id) {
        return ${aggregate?cap_first}EntryRepository.findOne(id);
    }

    @ApiOperation(value = "查询文章列表")
    @GetMapping(value = "/list")
    public Iterable
<${aggregate?cap_first}Entry> list(@QuerydslPredicate(root = ${aggregate?cap_first}Entry.class) Predicate predicate) {
    return ${aggregate?cap_first}EntryRepository.findAll(predicate);
    }

    @ApiOperation(value = "查询文章分页")
    @GetMapping(value = "/page")
    public Page
    <${aggregate?cap_first}Entry> page(@QuerydslPredicate(root = ${aggregate?cap_first}Entry.class) Predicate predicate,
        Pageable pageable) {
        return ${aggregate?cap_first}EntryRepository.findAll(predicate, pageable);
        }

        @ApiOperation(value = "新增文章")
        @PostMapping(value = "/create")
        public void create(@RequestBody @Valid ${aggregate?cap_first}CreateCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
        }

        @ApiOperation(value = "修改文章")
        @PutMapping(value = "/update")
        public void update(@RequestBody @Valid ${aggregate?cap_first}UpdateCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
        }

        @ApiOperation(value = "删除文章")
        @DeleteMapping(value = "/remove")
        public void delete(@RequestBody @Valid ${aggregate?cap_first}RemoveCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
        }
        }
