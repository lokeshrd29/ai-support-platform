package com.lokesh.ai_support_platform.auth.controller;

import com.lokesh.ai_support_platform.auth.dto.LoginRequest;
import com.lokesh.ai_support_platform.auth.dto.RegisterRequest;
import com.lokesh.ai_support_platform.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService ;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest , HttpServletRequest request)
    {
        return ResponseEntity.ok(authService.login(loginRequest,request));
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(HttpSession session) {

        String userName = (String) session.getAttribute("userName");

        return ResponseEntity.ok(userName);
    }

    @PostMapping("/logout")
    public  ResponseEntity<String> logout(HttpServletRequest request)
    {
        request.getSession().invalidate();

        return ResponseEntity.ok("Logged Out Successfully");
    }
}
