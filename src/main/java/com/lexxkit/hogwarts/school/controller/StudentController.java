package com.lexxkit.hogwarts.school.controller;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import com.lexxkit.hogwarts.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        if (age != null && age > 0) {
            return ResponseEntity.ok(studentService.findStudentsByAge(age));
        }
        if (minAge != null && maxAge != null && minAge > 0 && minAge <= maxAge) {
            return ResponseEntity.ok(studentService.findStudentsByAgeBetween(minAge, maxAge));
        }
        return ResponseEntity.ok(studentService.findAllStudents());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        if (createdStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFacultyForStudent(@PathVariable long id) {
        Faculty facultyForStudent = studentService.getFacultyForStudent(id);
        if (facultyForStudent == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facultyForStudent);
    }

    @GetMapping("/number")
    public ResponseEntity<Integer> getNumberOfStudents() {
        return ResponseEntity.ok(studentService.getNumberOfStudents());
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAgeOfStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeOfStudents());
    }

    @GetMapping("/five-last")
    public ResponseEntity<Collection<Student>> getFiveLastCreatedStudents() {
        return ResponseEntity.ok(studentService.getFiveLastCreatedStudents());
    }

    @GetMapping("/namewithA")
    public ResponseEntity<Collection<String>> getAllStudentsNameStartsWithACapitalize() {
        return ResponseEntity.ok(studentService.findAllStudentsNameStartsWithACapitalize());
    }

    @GetMapping("/average-age-stream")
    public ResponseEntity<Double> getAverageAgeOfStudentsWithStream() {
        return ResponseEntity.ok(studentService.getAverageAgeOfStudentsWithStream());
    }

    @GetMapping("/test-threads")
    public ResponseEntity<Void> printStudentsNamesInThreads() {
        studentService.printStudentsNamesInThreads();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test-synchronized-threads")
    public ResponseEntity<Void> printStudentsNamesWithThreadsSynchronized() {
        studentService.printStudentsNamesWithThreadsSynchronized();
        return ResponseEntity.ok().build();
    }
}
