package com.lin.imissyou.dto;

import com.lin.imissyou.validators.PasswordEqual;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@PasswordEqual
public class PersonDTO {
    private String password1;
    private String password2;
}
