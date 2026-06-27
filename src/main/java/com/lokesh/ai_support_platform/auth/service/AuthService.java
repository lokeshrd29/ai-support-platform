package com.lokesh.ai_support_platform.auth.service;

import com.lokesh.ai_support_platform.auth.dto.LoginRequest;
import com.lokesh.ai_support_platform.auth.dto.LoginResponse;
import com.lokesh.ai_support_platform.auth.dto.RegisterResponse;
import com.lokesh.ai_support_platform.auth.dto.RegisterRequest;
import com.lokesh.ai_support_platform.auth.entity.User;
import com.lokesh.ai_support_platform.auth.exception.InvalidCredentialsException;
import com.lokesh.ai_support_platform.auth.exception.UserAlreadyExistsException;
import com.lokesh.ai_support_platform.auth.repository.UserRepository;
import com.lokesh.ai_support_platform.auth.security.JwtService;
import com.lokesh.ai_support_platform.common.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService ;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email '" + request.getEmail() + "' already exists"
            );
        }

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new UserAlreadyExistsException(
                    "Username '" + request.getUserName() + "' already exists");
        }


        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .requestedRole(request.getRequestedRole())
                .enabled(true)
                .build();

        userRepository.save(user);

        return new RegisterResponse(
                request.getUserName(),
                "User Registered Successfully"
        );
    }

    public LoginResponse login(LoginRequest loginRequest)
    {

        User user = userRepository.getUserByUserName(loginRequest.getUserName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        // Prepare claims to embed inside the JWT payload (Replaces session attributes!)
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getRole());

        // Generate the signed JWT token.
        // (Note: This assumes your User entity implements Spring Security's UserDetails interface)
        String token = jwtService.generateToken(extraClaims, user);

        return new LoginResponse(
                token,
                user.getUserName(),
                user.getRole()
        );
    }
}
