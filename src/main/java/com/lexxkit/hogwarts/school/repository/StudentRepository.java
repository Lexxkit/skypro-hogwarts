package com.lexxkit.hogwarts.school.repository;

import com.lexxkit.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getNumberOfStudents();
}
