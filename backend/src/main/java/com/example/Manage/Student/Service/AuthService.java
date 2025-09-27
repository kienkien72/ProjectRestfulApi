package com.example.Manage.Student.Service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Manage.Student.Config.JwtUtil;
import com.example.Manage.Student.Dto.request.LoginRequesDTO;
import com.example.Manage.Student.Dto.response.LoginResponseDTO;
import com.example.Manage.Student.Entity.BlackListToken;
import com.example.Manage.Student.Entity.Student;
import com.example.Manage.Student.Repository.BlackListRepository;
import com.example.Manage.Student.Repository.StudentRepository;

@Service
public class AuthService {
    private StudentRepository studentRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private BlackListRepository blackListRepository;

    public AuthService(StudentRepository studentRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder,
            BlackListRepository blackListRepository) {
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.blackListRepository = blackListRepository;
    }

    // Login
    public LoginResponseDTO loginStudent(String email, String password) {
        Student student = studentRepository.findByEmail(email);

        if (student != null && passwordEncoder.matches(password, student.getPassword())) {
            String token = jwtUtil.generateToken(email, student.getRole());
            return new LoginResponseDTO(token, student.getRole());
        }

        return null;
    }

    // Logout
    public void logout(String token) {
        Date expiryDate = jwtUtil.extractExpiry(token);
        BlackListToken blackListToken = new BlackListToken(0, token, expiryDate);

        blackListRepository.save(blackListToken);
    }

}
