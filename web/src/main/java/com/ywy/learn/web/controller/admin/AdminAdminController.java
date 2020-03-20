package com.ywy.learn.web.controller.admin;

import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.web.controller.base.BaseAdminController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */

@Api(value = "AdminBaseAdminController", tags = "后台")
@Validated
@RequestMapping(value = "/admin/admin")
@RestController
public class AdminAdminController extends BaseAdminController {

    @Autowired
    AdminEntryRepository adminEntryRepository;

    @GetMapping("info")
    public AdminEntry info() {
        return getAdmin();
    }

}
