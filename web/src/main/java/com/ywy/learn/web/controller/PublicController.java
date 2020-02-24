package com.ywy.learn.web.controller;

import com.ywy.learn.command.admin.api.command.AdminLoginCommand;
import com.ywy.learn.infrastructure.exception.BusinessException;
import com.ywy.learn.infrastructure.util.MailUtils;
import com.ywy.learn.infrastructure.util.OtherUtils;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.entry.QAdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.query.repository.UserEntryRepository;
import com.ywy.learn.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.axonframework.messaging.MetaData;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */

@Api(value = "PublicController", tags = "公共")
@Validated
@RequestMapping(value = "/public")
@RestController
public class PublicController extends BaseController {

    @Autowired
    UserEntryRepository userEntryRepository;

    @Autowired
    AdminEntryRepository adminEntryRepository;

    @Autowired
    StringRedisTemplate redisTemplate;

    @ApiOperation(value = "admin登录")
    @PutMapping(value = "/login")
    public String login(@RequestBody @Valid AdminLoginCommand command) {
        AdminEntry adminEntry = adminEntryRepository.findOne(QAdminEntry.adminEntry.username.eq(command.getUsername()));
        if (adminEntry == null) {
            throw new BusinessException("用户不存在");
        }
        command.setId(adminEntry.getId());
        return sendAndWait(command, MetaData.emptyInstance());
    }

    @ApiOperation(value = "发送邮箱验证码")
    @PutMapping(value = "/validate_code")
    public String validateCode(@RequestParam("email") @NotBlank String email) {
        // 生成一个验证码
        String code = OtherUtils.rendomCode(6);
        // 发送邮件
        MailUtils.sendMail("验证码", "验证码为:<br/><br/><br/>" +
                "<h1>" + code + "</h1>", email);
        // 存入redis设置ttl
        redisTemplate.opsForValue().set("" + email, code, 10L, TimeUnit.MINUTES);
        return code;
    }

    @ApiOperation(value = "发送邮箱")
    @PutMapping(value = "/email")
    public void sendCode(@NotBlank @RequestParam("email") String email,
                         @NotBlank @RequestParam("title") String title,
                         @NotBlank @RequestParam("content") String content) {
        // 发送邮件
        MailUtils.sendMail(title, content, email);
    }
}
