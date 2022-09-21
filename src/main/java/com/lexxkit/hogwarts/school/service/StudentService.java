package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import com.lexxkit.hogwarts.school.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Collection<String> findAllStudentsNameStartsWithACapitalize() {
        logger.info("Was invoked method for find All students with name starts with 'A' capitalize");
        return findAllStudents().stream()
                .filter(s -> s.getName().startsWith("A"))
                .map(s -> s.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAverageAgeOfStudentsWithStream() {
        logger.info("Was invoked method for calculate students average age with stream usage");
        return findAllStudents().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public void printStudentsNamesInThreads() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            System.out.println("Add more Students to DB.");
            return;
        }
        logger.info("Print initial order of names:");
        for (Student student : students) {
            System.out.println(student.getName());
        }

        logger.info("Print names in 3 threads:");

        printStudentName(students, 0);
        printStudentName(students, 1);

        new Thread(() -> {
            printStudentName(students, 2);
            printStudentName(students, 3);
        }).start();

        new Thread(() -> {
            printStudentName(students, 4);
            printStudentName(students, 5);
        }).start();
    }

    public void printStudentsNamesWithThreadsSynchronized() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            System.out.println("Add more Students to DB.");
            return;
        }

        logger.info("Print names in 3 threads SYNCHRONIZED:");

        printStudentNameSynchronized(students, 0);
        printStudentNameSynchronized(students, 1);

        new Thread(() -> {
            printStudentNameSynchronized(students, 2);
            printStudentNameSynchronized(students, 3);
        }).start();

        new Thread(() -> {
            printStudentNameSynchronized(students, 4);
            printStudentNameSynchronized(students, 5);
        }).start();
    }

    private void printStudentName(List<Student> students, int index) {
        System.out.println(students.get(index).getName());

        slowComputation();
    }

    private synchronized void printStudentNameSynchronized(List<Student> students, int i) {
        System.out.println(students.get(i).getName());

        slowComputation();
    }

    private void slowComputation() {
        // The code below is used SOLELY to slow down the computation process
        // to show how Threads work.
        String s = "";
        for (int i = 0; i < 50_000; i++) {
            s += i;
        }
    }
}
