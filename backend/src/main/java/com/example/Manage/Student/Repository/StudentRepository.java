package com.example.Manage.Student.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Manage.Student.Entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    boolean existsByEmail(String email);
}
