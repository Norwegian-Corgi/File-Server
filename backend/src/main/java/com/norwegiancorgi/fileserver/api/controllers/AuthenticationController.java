package com.norwegiancorgi.fileserver.api.controllers;

import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotCreatedException;
import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotDeletedException;
import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotFoundException;
import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotUpdatedException;
import com.norwegiancorgi.fileserver.api.models.AuthenticationRequest;
import com.norwegiancorgi.fileserver.api.models.AuthenticationResponse;
import com.norwegiancorgi.fileserver.api.models.PasswordUpdateRequest;
import com.norwegiancorgi.fileserver.api.models.RegisterRequest;
import com.norwegiancorgi.fileserver.internal.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws UserNotCreatedException {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws UserNotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PutMapping
    public ResponseEntity<AuthenticationResponse> update(@RequestBody PasswordUpdateRequest passwordUpdateRequest) throws UserNotUpdatedException {
        return ResponseEntity.ok(authenticationService.update(passwordUpdateRequest));
    }

    @DeleteMapping
    public void delete(@RequestBody AuthenticationRequest authenticationRequest) throws UserNotDeletedException {
        authenticationService.delete(authenticationRequest);
    }
}
