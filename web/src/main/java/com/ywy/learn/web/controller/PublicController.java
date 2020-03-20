package com.ywy.learn.web.controller;

import com.ywy.learn.command.admin.api.command.AdminLoginCommand;
import com.ywy.learn.command.user.api.command.UserCreateCommand;
import com.ywy.learn.command.user.api.command.UserLoginCommand;
import com.ywy.learn.common.api.exception.BusinessError;
import com.ywy.learn.common.api.exception.BusinessException;
import com.ywy.learn.common.api.security.AsymmetricEncryptionKit;
import com.ywy.learn.common.api.security.SecurityKit;
import com.ywy.learn.common.api.util.MailUtils;
import com.ywy.learn.common.api.util.OtherUtils;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.entry.QAdminEntry;
import com.ywy.learn.query.entry.QUserEntry;
import com.ywy.learn.query.entry.UserEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.query.repository.UserEntryRepository;
import com.ywy.learn.web.controller.base.BaseController;
import com.ywy.learn.web.pojo.CertLoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.common.IdentifierFactory;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.cert.X509Certificate;
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

    @ApiOperation(value = "管理员登录")
    @PutMapping(value = "/login_admin")
    public String adminLogin(@RequestBody @Valid AdminLoginCommand command) {
        AdminEntry adminEntry = adminEntryRepository.findOne(QAdminEntry.adminEntry.username.eq(command.getUsername()));
        if (adminEntry == null) {
            throw new BusinessException(BusinessError.BU_9600);
        }
        command.setId(adminEntry.getId());
        command.setLastToken(UUID.randomUUID().toString());
        sendAndWait(command);
        return command.getLastToken();
    }

    @ApiOperation(value = "邮箱用户登录", notes = "不传参数vCode为获取邮箱验证码,传了vCode为登录")
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
                throw new BusinessException(BusinessError.BU_9201);
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

    @ApiOperation(value = "证书登录1.获取随机登录验证字符串")
    @GetMapping(value = "/cent_str")
    public String userLogin() {
        String plaintext = Base64.toBase64String(UUID.randomUUID().toString().getBytes());
        redisTemplate.opsForValue().set(plaintext, "1", 10, TimeUnit.MINUTES);
        return plaintext;
    }

    @ApiOperation(value = "证书登录2.使用账号+密文登录", notes = "1.将第一步获取的字符串使用base64解码成流\n" +
            "2.使用私钥以对流进行加密得到密文流\n" +
            "3.对密文流base64编码得到参数ciphertext")
    @PostMapping(value = "/cent_login")
    public String userLogin(@RequestBody @Valid CertLoginDTO certLoginDTO) {
        if (!redisTemplate.hasKey(certLoginDTO.getPlaintext())) {
            throw new BusinessException(BusinessError.BU_9202);
        }
        UserEntry userEntry = userEntryRepository.findOne(QUserEntry.userEntry.email.eq(certLoginDTO.getEmail()));
        if (userEntry == null) {
            throw new BusinessException(BusinessError.BU_9202);
        }
        if (StringUtils.isBlank(userEntry.getCertId())) {
            throw new BusinessException(BusinessError.BU_9202);
        }
        X509Certificate x509Certificate;
        try {
            x509Certificate = SecurityKit.getCert(userEntry.getCertId());
        } catch (Exception e) {
            throw new BusinessException(BusinessError.BU_9202);
        }
        String result;
        try {
            result = Base64.toBase64String(AsymmetricEncryptionKit.decrypt(Base64.decode(certLoginDTO.getCiphertext()), x509Certificate.getPublicKey()));
        } catch (Exception e) {
            throw new BusinessException(BusinessError.BU_9202);
        }
        if (!certLoginDTO.getPlaintext().equals(result)) {
            throw new BusinessException(BusinessError.BU_9202);
        }
        UserLoginCommand command = new UserLoginCommand();
        command.setId(userEntry.getId());
        command.setLastToken(UUID.randomUUID().toString());
        sendAndWait(command);

        redisTemplate.delete(certLoginDTO.getPlaintext());
        return command.getLastToken();
    }
}
