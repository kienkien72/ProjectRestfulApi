package com.example.Manage.Student.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Manage.Student.Dto.request.LoginRequesDTO;
import com.example.Manage.Student.Dto.response.LoginResponseDTO;

import com.example.Manage.Student.Service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/students")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginStudent(@RequestBody LoginRequesDTO requesDTO) {
        LoginResponseDTO loginResponseDTO = authService.loginStudent(requesDTO.getEmail(), requesDTO.getPassword());

        if (loginResponseDTO != null) {
            return ResponseEntity.ok(loginResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<?> logoutStudent(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String token = authHeader.substring(7);

            authService.logout(token);
            return ResponseEntity.ok("Logout thành công");
        }
        return ResponseEntity.badRequest().body("No token provided");
    }
}
