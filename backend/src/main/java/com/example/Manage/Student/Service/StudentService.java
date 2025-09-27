package com.example.Manage.Student.Service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;

import com.example.Manage.Student.Entity.Student;
import com.example.Manage.Student.Repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Lấy danh sách học sinh có phân trang
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // Lấy theo từng học sinh
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Tạo mới
    public Student createStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("STUDENT");

        return studentRepository.save(student);
    }

    // Xoá
    public Optional<Student> deleteStudent(Long id) {
        try {
            studentRepository.deleteById(id);
            return studentRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Student not found");
        }
    }

    // Sửa
    public Optional<Student> updateStudent(Long id, Student student) {
        return studentRepository.findById(id).map(StudentExit -> {
            StudentExit.setName(student.getName());
            StudentExit.setEmail(student.getEmail());
            StudentExit.setPhone(student.getPhone());
            StudentExit.setAddress(student.getAddress());
            return studentRepository.save(StudentExit);
        });
    }

}
