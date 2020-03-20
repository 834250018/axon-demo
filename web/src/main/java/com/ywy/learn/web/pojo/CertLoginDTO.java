package com.ywy.learn.web.pojo;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author ve
 * @date 2019/7/2 18:06
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CertLoginDTO<T> implements Serializable {

    @NotBlank
    @ApiParam("登录临时验证字符串明文")
    String plaintext;
    @NotBlank
    @ApiParam("登录临时验证字符串密文")
    String ciphertext;
    @NotBlank
    @ApiParam("登录邮箱")
    private String email;
}
