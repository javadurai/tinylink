package com.github.jabadurai.tinylink.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    private String currentPassword;

    private String newPassword;

    private String confirmPassword;

}
