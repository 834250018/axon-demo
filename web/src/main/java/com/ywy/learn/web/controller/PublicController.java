package com.ywy.learn.web.controller;

import com.ywy.learn.command.admin.api.command.AdminLoginCommand;
import com.ywy.learn.common.util.MailUtils;
import com.ywy.learn.common.util.OtherUtils;
import com.ywy.learn.query.entry.QAdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.query.repository.UserEntryRepository;
import com.ywy.learn.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */

@Api(value = "UserController", tags = "公共")
@Validated
@RequestMapping(value = "/user")
@RestController
public class PublicController extends BaseController {

    @Autowired
    UserEntryRepository userEntryRepository;

    @Autowired
    AdminEntryRepository adminEntryRepository;

    // todo 临时用map代替redis
    public final static ConcurrentHashMap<String, Object> redis = new ConcurrentHashMap();

    @ApiOperation(value = "admin登录")
    @PutMapping(value = "/update")
    public void update(@RequestBody @Valid AdminLoginCommand command) {
        if (command.getCode().equals(redis.get(command.getUsername()))) {
            redis.remove(command.getUsername());
            redis.put(OtherUtils.getGUID(), adminEntryRepository.findOne(QAdminEntry.adminEntry.username.eq(command.getUsername())));
        }
    }

    @ApiOperation(value = "发送邮箱验证码")
    @PutMapping(value = "/email")
    public String sendCode(@NotBlank String email) {
        // 生成一个验证码
        String code = OtherUtils.rendomCode(6);
        // 发送邮件
        MailUtils.sendMail("验证码", "您的验证码为:<br/>" + code + "感谢您的体验---by ve", email);
        // 存入redis设置ttl
        redis.put(email, code);
        return code;
    }
}
