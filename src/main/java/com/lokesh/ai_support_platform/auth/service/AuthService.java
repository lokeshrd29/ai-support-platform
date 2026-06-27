package com.lokesh.ai_support_platform.auth.service;

import com.lokesh.ai_support_platform.auth.dto.LoginRequest;
import com.lokesh.ai_support_platform.auth.dto.LoginResponse;
import com.lokesh.ai_support_platform.auth.dto.RegisterRequest;
import com.lokesh.ai_support_platform.auth.entity.User;
import com.lokesh.ai_support_platform.auth.exception.InvalidCredentialsException;
import com.lokesh.ai_support_platform.auth.exception.UserAlreadyExistsException;
import com.lokesh.ai_support_platform.auth.repository.UserRepository;
import com.lokesh.ai_support_platform.common.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponse register(RegisterRequest request) {

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

        return new LoginResponse(
                request.getUserName(),
                "User Registered Successfully"
        );
    }

    public String login(LoginRequest loginRequest,
                        HttpServletRequest request)
    {
        User user = userRepository.getUserByUserName(loginRequest.getUserName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        HttpSession session = request.getSession(true);

        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("role", user.getRole());

        return "Login Successful" ;
    }
}
