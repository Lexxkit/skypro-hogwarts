package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long idCounter = 0;

    public Student createStudent(Student student) {
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
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }
}
