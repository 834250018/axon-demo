package com.ywy.learn.web.controller.user;

import com.ywy.learn.command.user.api.command.UserApplyCertCommand;
import com.ywy.learn.command.user.api.command.UserLoginCommand;
import com.ywy.learn.command.user.api.event.UserLoginedEvent;
import com.ywy.learn.infrastructure.security.SecurityKit;
import com.ywy.learn.query.entry.UserEntry;
import com.ywy.learn.query.repository.UserEntryRepository;
import com.ywy.learn.web.controller.base.BaseUserController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.security.x509.X500Name;

import java.security.PublicKey;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */

@Api(value = "UserBaseUserController", tags = "前台")
@Validated
@RequestMapping(value = "/user/user")
@RestController
public class UserUserController extends BaseUserController {

    @Autowired
    UserEntryRepository userEntryRepository;

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/info")
    public UserEntry info() {
        return getUser();
    }

    @ApiOperation(value = "设置证书")
    @PostMapping(value = "/apply_cert")
    public String applyCert(@RequestParam("publicKeyBase64") String publicKeyBase64) throws Exception {
        String email = getUser().getEmail();
        String oldCert = getUser().getCertId();
        if(!StringUtils.isBlank(oldCert)) {
            // todo 若旧证书不为空且没过期,则不允许申请
        }
        PublicKey publicKey = SecurityKit.getPublicKey(publicKeyBase64);
        String certId = SecurityKit.genSignedCertificate(publicKey, new X500Name(email, email, email, email), 365 * 24 * 60 * 60L);
        UserApplyCertCommand command = new UserApplyCertCommand();
        command.setId(getUserId());
        command.setCertId(certId);
        sendAndWait(command);
        return certId;
    }

}
