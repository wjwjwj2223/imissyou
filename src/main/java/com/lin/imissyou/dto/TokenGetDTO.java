package com.lin.imissyou.dto;

import com.lin.imissyou.core.enumeration.LoginType;
import com.lin.imissyou.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class TokenGetDTO {

    @NotBlank(message = "account不允许为空")
    private String account;
    @TokenPassword(max = 30, message = "{token.password}")
    private String password;

    private LoginType type;
}
