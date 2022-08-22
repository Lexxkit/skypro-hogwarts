package com.lexxkit.hogwarts.school.repository;

import com.lexxkit.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
