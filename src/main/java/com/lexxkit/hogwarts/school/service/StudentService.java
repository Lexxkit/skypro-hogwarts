package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long idCounter = 0;

    public Student createStudent(Student student) {
        if (students.containsKey(student.getId())){
            return null;
        }
        student.setId(++idCounter);
        students.put(idCounter, student);
        return student;
    }

    public Collection<Student> findAllStudents() {
        return students.values();
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> findStudentsByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
