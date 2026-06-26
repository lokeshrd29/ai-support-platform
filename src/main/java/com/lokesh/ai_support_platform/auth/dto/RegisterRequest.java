package com.lokesh.ai_support_platform.auth.dto;

import com.lokesh.ai_support_platform.common.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotNull
    private String userName ;

    @Email
    private String email ;

    @NotNull
    private String password ;

    @NotNull
    private Role requestedRole ;

}
