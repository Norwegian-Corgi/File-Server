package com.norwegiancorgi.fileserver.internal.services;

import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotDeletedException;
import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotFoundException;
import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotUpdatedException;
import com.norwegiancorgi.fileserver.api.models.AuthenticationRequest;
import com.norwegiancorgi.fileserver.api.models.AuthenticationResponse;
import com.norwegiancorgi.fileserver.api.models.PasswordUpdateRequest;
import com.norwegiancorgi.fileserver.api.models.RegisterRequest;
import com.norwegiancorgi.fileserver.internal.entities.Userz;
import com.norwegiancorgi.fileserver.api.exceptions.user.UserNotCreatedException;
import com.norwegiancorgi.fileserver.internal.enums.Role;
import com.norwegiancorgi.fileserver.internal.repositories.IUserRepository;
import com.norwegiancorgi.fileserver.internal.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new User
     * @param registerRequest Request containing name, email, password and role of the user to be created
     * @return Authentication response when the user is created successfully
     * @throws UserNotCreatedException When user is not created
     */
    public AuthenticationResponse register(RegisterRequest registerRequest) throws UserNotCreatedException {
        final Userz requester = userRepository.findById(registerRequest.getRequester()).orElseThrow(() -> new UserNotCreatedException("Requester not found"));
        if (requester.getRole().equals(Role.ADMIN)) {
            final Userz createdUser = this.createUser(registerRequest);
            try {
                FileUtil.createFolder(createdUser.getId().toString());
            } catch (Exception e) {
                userRepository.delete(createdUser);
                throw new UserNotCreatedException("Unable to create user.", e);
            }
            return this.createAuthenticationResponse(createdUser);
        }
        throw new UserNotCreatedException("Unable to create user. Requester does not have the rights!");
    }

    /**
     * Authenticates a user using email and password
     * @param authenticationRequest Request containing email and password
     * @return Authentication response when the user is authenticated successfully
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws UserNotFoundException {
        final Userz userz = this.getUser(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        return this.createAuthenticationResponse(userz);
    }

    /**
     * Updates the password
     * @param passwordUpdateRequest Request containing user email, old and new passwords
     * @return Authentication response when the user is updated successfully
     */
    public AuthenticationResponse update(PasswordUpdateRequest passwordUpdateRequest) throws UserNotUpdatedException {
        final Userz userz;
        try {
            userz = this.getUser(passwordUpdateRequest.getEmail(), passwordUpdateRequest.getOldPassword());
        } catch (UserNotFoundException e) {
            throw new UserNotUpdatedException(e);
        }
        userz.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        final Userz updateUser = userRepository.save(userz);
        return this.createAuthenticationResponse(updateUser);
    }

    /**
     * Deletes the user and all the files associated with it
     * @param authenticationRequest Request containing email and password
     * @throws UserNotDeletedException When the user is not deleted
     */
    public void delete(AuthenticationRequest authenticationRequest) throws UserNotDeletedException {
        final Userz userz;
        try {
            userz = this.getUser(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            FileUtil.deleteFiles(userz.getId().toString());
        } catch (UserNotFoundException | IOException e) {
            throw new UserNotDeletedException(e);
        }
        userRepository.delete(userz);
    }

    /**
     * Helper function ot create a new user
     * @param registerRequest Request containing name, email, password and role of the user to be created
     * @return The newly created user
     */
    private Userz createUser(RegisterRequest registerRequest) {
        final Userz userz = new Userz();
        userz.setName(registerRequest.getName());
        userz.setEmail(registerRequest.getEmail());
        userz.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userz.setRole(registerRequest.getRole());
        return userRepository.save(userz);
    }

    /**
     * Helper function to create the authentication response for the user
     * @param userz User for which the authentication response needs to be created
     * @return Authentication response containing the user UUID and jwtToken
     */
    private AuthenticationResponse createAuthenticationResponse(Userz userz) {
        final String jwtToken = jwtService.generateToken(userz);
        return new AuthenticationResponse(userz.getId(), jwtToken);
    }

    /**
     * Authenticates the email and password and returns the user with the provided email
     * @param email User's email
     * @param password User's password
     * @return The user
     */
    private Userz getUser(String email, String password) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(String.format("No user found with the email: %s", email)));
    }
}
