package com.example.demo.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterVO {
    @ApiModelProperty(value ="密码")
    private String username;

    @ApiModelProperty(value ="电话号码")
    private String phone;

    @ApiModelProperty(value ="验证码")
    private String code;

    @ApiModelProperty(value ="密码")
    private String password;
}
