package com.norwegiancorgi.fileserver.api.models;

import com.norwegiancorgi.fileserver.internal.enums.Role;
import lombok.Data;
import java.util.UUID;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
    private UUID requester;
}
