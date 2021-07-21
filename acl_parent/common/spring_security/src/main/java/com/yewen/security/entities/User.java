package com.yewen.security.entities;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ShadowStart
 * @create 2021-07-18 9:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户实体类")
public class User implements Serializable {

    public static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "微信openid")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "名称")
    private String nickName;

    @ApiModelProperty(value = "用户头像")
    private String salt;

    @ApiModelProperty(value = "用户签名")
    private String token;


}
