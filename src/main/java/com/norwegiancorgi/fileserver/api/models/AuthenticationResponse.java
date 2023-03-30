package com.norwegiancorgi.fileserver.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthenticationResponse {
    @NonNull
    private UUID userUuid;
    @NonNull
    private String token;
}
