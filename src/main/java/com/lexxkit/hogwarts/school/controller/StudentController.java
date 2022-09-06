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
}
