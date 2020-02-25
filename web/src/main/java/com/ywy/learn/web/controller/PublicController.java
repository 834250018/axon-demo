package com.ywy.learn.web.controller;

import com.ywy.learn.command.admin.api.command.AdminLoginCommand;
import com.ywy.learn.command.user.api.command.UserCreateCommand;
import com.ywy.learn.command.user.api.command.UserLoginCommand;
import com.ywy.learn.infrastructure.exception.BusinessException;
import com.ywy.learn.infrastructure.util.MailUtils;
import com.ywy.learn.infrastructure.util.OtherUtils;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.entry.QAdminEntry;
import com.ywy.learn.query.entry.QUserEntry;
import com.ywy.learn.query.entry.UserEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.query.repository.UserEntryRepository;
import com.ywy.learn.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.common.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
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

    @ApiOperation(value = "管理员")
    @PutMapping(value = "/login_admin")
    public String adminLogin(@RequestBody @Valid AdminLoginCommand command) {
        AdminEntry adminEntry = adminEntryRepository.findOne(QAdminEntry.adminEntry.username.eq(command.getUsername()));
        if (adminEntry == null) {
            throw new BusinessException("用户不存在");
        }
        command.setId(adminEntry.getId());
        command.setLastToken(UUID.randomUUID().toString());
        return sendAndWait(command);
    }

    @ApiOperation(value = "邮箱用户登录", notes = "不传参数vCode为发送邮箱验证码,传了vCode为登录")
    @PostMapping(value = "/login_user")
    public String userLogin(@RequestParam("email") String email, @RequestParam(value = "vCode", required = false) String vCode) {
        if (StringUtils.isBlank(vCode)) {
            // 生成一个验证码
            String code = OtherUtils.rendomCode(6);
            // 发送邮件
            MailUtils.sendMail("验证码", "验证码为:<h1>" + code + "</h1></br>感谢使用!</br>ve", email);
            // 存入redis设置ttl
            redisTemplate.opsForValue().set("login_vcode_" + email, code, 10L, TimeUnit.MINUTES);
            return null;
        } else {
            if (!vCode.equals(redisTemplate.opsForValue().get("login_vcode_" + email))) {
                throw new BusinessException("验证码不存在");
            }
            redisTemplate.delete("login_vcode_" + email);
            // 查看用户是否存在
            UserEntry userEntry = userEntryRepository.findOne(QUserEntry.userEntry.email.eq(email));
            UserCreateCommand command = new UserCreateCommand();
            UserLoginCommand userLoginCommand = new UserLoginCommand();
            if (userEntry == null) {
                command.setId(IdentifierFactory.getInstance().generateIdentifier());
                command.setEmail(email);
                sendAndWait(command);
                userLoginCommand.setId(command.getId());
            } else {
                userLoginCommand.setId(userEntry.getId());
            }
            userLoginCommand.setLastToken(UUID.randomUUID().toString());
            sendAndWait(userLoginCommand);
            return userLoginCommand.getLastToken();
        }
    }
}
