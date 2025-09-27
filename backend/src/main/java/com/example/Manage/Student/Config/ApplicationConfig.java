package com.example.Manage.Student.Config;

import com.example.Manage.Student.Entity.Student;
import com.example.Manage.Student.Repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    CommandLineRunner initAdmin(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@gmail.com";
            String adminPassword = "123456";

            if (studentRepository.findByEmail(adminEmail) == null) {
                Student admin = new Student();
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ADMIN"); // DB lưu ADMIN
                admin.setName("Super Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPhone("0000000000");
                admin.setAddress("Admin Address");

                studentRepository.save(admin);
                System.out.println("✅ Admin account created: " + adminEmail + " / " + adminPassword);
            } else {
                System.out.println("ℹ Admin account already exists.");
            }
        };
    }
}
