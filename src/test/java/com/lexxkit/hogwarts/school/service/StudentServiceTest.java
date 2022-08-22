package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Student;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.lexxkit.hogwarts.school.service.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class StudentServiceTest {

    private final StudentService out = new StudentService();

    @Test
    void shouldCreateNewStudent() {
        Student result = out.createStudent(POTTER);

        assertThat(result).isEqualTo(POTTER);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(out.findAllStudents()).hasSize(1);
    }

    @Test
    void shouldReturnNullWhenCreateTheSameStudent() {
        out.createStudent(POTTER);
        Student result = out.createStudent(POTTER);

        assertThat(result).isNull();
    }

    @Test
    void shouldFindStudentById() {
        out.createStudent(POTTER);
        out.createStudent(MALFOY);
        Student result = out.findStudent(1L);

        assertThat(result).isEqualTo(POTTER);
    }

    @Test
    void shouldReturnAllStudent() {
        out.createStudent(POTTER);
        out.createStudent(MALFOY);
        Collection<Student> result = out.findAllStudents();

        assertThat(result).hasSize(2);
        assertThat(result).contains(POTTER, MALFOY);
    }

    @Test
    void shouldUpdateStudent() {
        out.createStudent(POTTER);
        Student result = out.updateStudent(UPD_POTTER);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(UPD_POTTER);
    }

    @Test
    void shouldReturnNullIfStudentNotFoundWhenUpdateStudent() {
        Student result = out.updateStudent(UPD_POTTER);

        assertThat(result).isNull();
    }

    @Test
    void shouldDeleteStudent() {
        out.createStudent(POTTER);
        Student result = out.deleteStudent(1L);

        assertThat(result).isEqualTo(POTTER);
        assertThat(out.findAllStudents()).hasSize(0);
    }

    @Test
    void shouldReturnStudentsWithSpecificAge() {
        out.createStudent(POTTER);
        out.createStudent(GRANGER);
        out.createStudent(MALFOY);
        Collection<Student> result = out.findStudentsByAge(AGE);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(POTTER, GRANGER);
    }
}