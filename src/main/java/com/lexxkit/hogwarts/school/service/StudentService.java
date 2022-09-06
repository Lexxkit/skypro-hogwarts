package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import com.lexxkit.hogwarts.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Collection<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyForStudent(long id) {
        Student student = findStudent(id);
        Optional<Faculty> faculty = Optional.ofNullable(student.getFaculty());
        return faculty.orElse(null);
    }

    public Integer getNumberOfStudents() {
       return studentRepository.getNumberOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }
}
