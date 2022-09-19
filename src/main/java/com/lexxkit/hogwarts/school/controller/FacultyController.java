package com.lexxkit.hogwarts.school.controller;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import com.lexxkit.hogwarts.school.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String name
    ) {
        if (color != null && !color.isBlank() && (name == null || name.isBlank())) {
            return ResponseEntity.ok(facultyService.findFacultyByColor(color));
        }
        if (color != null && !color.isBlank() && name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(name, color));
        }
        return ResponseEntity.ok(facultyService.findAllFaculties());
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        if (createdFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsForFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getStudentsForFaculty(id));
    }

    @GetMapping("/longest-name")
    public ResponseEntity<String> getTheLongestFacultyName() {
        String theLongestFacultyName = facultyService.findTheLongestFacultyName();
        if (theLongestFacultyName == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(theLongestFacultyName);
    }
}
