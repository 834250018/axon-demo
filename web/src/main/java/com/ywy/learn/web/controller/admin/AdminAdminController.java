package com.ywy.learn.web.controller.admin;

import com.querydsl.core.types.Predicate;
import com.ywy.learn.command.admin.api.command.AdminCreateCommand;
import com.ywy.learn.command.admin.api.command.AdminRemoveCommand;
import com.ywy.learn.command.admin.api.command.AdminUpdateCommand;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.web.controller.base.AdminController;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */

@Api(value = "AdminAdminController", tags = "后台")
@Validated
@RequestMapping(value = "/admin/admin")
@RestController
public class AdminAdminController extends AdminController {

    @Autowired
    AdminEntryRepository adminEntryRepository;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("info")
    public AdminEntry info() {
        return getAdmin();
    }

}
