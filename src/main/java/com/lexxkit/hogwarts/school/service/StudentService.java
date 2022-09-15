package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import com.lexxkit.hogwarts.school.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Collection<Student> findAllStudents() {
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student with id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for save student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student with id: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentsByAge(int age) {
        logger.info("Was invoked method for find students by age");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for find students by age between {} and {}", minAge, maxAge);
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyForStudent(long id) {
        logger.info("Was invoked method get faculty for student");
        Student student = findStudent(id);
        Optional<Faculty> faculty = Optional.ofNullable(student.getFaculty());
        return faculty.orElse(null);
    }

    public Integer getNumberOfStudents() {
        logger.info("Was invoked method get total number of students");
       return studentRepository.getNumberOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for calculate students average age");
        return studentRepository.getAverageAgeOfStudents();
    }

    public Collection<Student> getFiveLastCreatedStudents() {
        logger.info("Was invoked method get five most recent students");
        return studentRepository.getFiveLastCreatedStudents();
    }
}
