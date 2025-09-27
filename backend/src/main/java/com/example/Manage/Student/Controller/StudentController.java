package com.example.Manage.Student.Controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.Manage.Student.Entity.Student;
import com.example.Manage.Student.Service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Xem thông tin học sinh theo ID
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @GetMapping("/my-info/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Tạo mới học sinh
    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity
                .status(201) // HTTP 201 Created
                .body(createdStudent);
    }

    // Xóa học sinh
    @PostMapping("/delete/{id}")
    public Optional<Student> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    // Cập nhật thông tin học sinh
    @PostMapping("/update/{id}")
    public Optional<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    // API phân trang
    @GetMapping("/all")
    public Page<Student> getStudents(
            @RequestParam(defaultValue = "0") int page, // page = số trang (0-based)
            @RequestParam(defaultValue = "5") int size, // size = số phần tử mỗi trang
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return studentService.getAllStudents(pageable);
    }

}