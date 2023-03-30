package com.norwegiancorgi.fileserver.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PasswordUpdateRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
