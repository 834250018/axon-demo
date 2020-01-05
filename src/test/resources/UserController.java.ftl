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

    @ApiOperation(value = "${aggregate}-one")
    @ApiParam
    @GetMapping(value = "/one")
    public ${aggregate?cap_first}Entry one(@NotBlank @RequestParam(value = "id") String id) {
        return ${aggregate?cap_first}EntryRepository.findOne(id);
    }

    @ApiOperation(value = "${aggregate}-list")
    @GetMapping(value = "/list")
    public Iterable
<${aggregate?cap_first}Entry> list(@QuerydslPredicate(root = ${aggregate?cap_first}Entry.class) Predicate predicate) {
    return ${aggregate?cap_first}EntryRepository.findAll(predicate);
    }

    @ApiOperation(value = "${aggregate}-page")
    @GetMapping(value = "/page")
    public Page
    <${aggregate?cap_first}Entry> page(@QuerydslPredicate(root = ${aggregate?cap_first}Entry.class) Predicate predicate,
        Pageable pageable) {
        return ${aggregate?cap_first}EntryRepository.findAll(predicate, pageable);
        }

        @ApiOperation(value = "${aggregate}-create")
        @PostMapping(value = "/create")
        public void create(@RequestBody @Valid ${aggregate?cap_first}CreateCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
        }

        @ApiOperation(value = "${aggregate}-update")
        @PutMapping(value = "/update")
        public void update(@RequestBody @Valid ${aggregate?cap_first}UpdateCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
        }

        @ApiOperation(value = "${aggregate}-delete")
        @DeleteMapping(value = "/remove")
        public void delete(@RequestBody @Valid ${aggregate?cap_first}RemoveCommand command) {
        sendAndWait(command, MetaData.emptyInstance());
        }
        }
