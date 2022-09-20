package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import com.lexxkit.hogwarts.school.repository.StudentRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.lexxkit.hogwarts.school.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService out ;

    @Test
    void shouldCreateNewStudent() {
        when(studentRepository.save(POTTER)).thenReturn(POTTER);
        Student result = out.createStudent(POTTER);

        assertThat(result).isEqualTo(POTTER);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @Disabled
    void shouldReturnNullWhenCreateTheSameStudent() {
        out.createStudent(POTTER);
        Student result = out.createStudent(POTTER);

        assertThat(result).isNull();
    }

    @Test
    void shouldFindStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(POTTER));
        Student result = out.findStudent(1L);

        assertThat(result).isEqualTo(POTTER);
    }

    @Test
    void shouldReturnAllStudent() {
        when(studentRepository.findAll()).thenReturn(List.of(POTTER, MALFOY));
        Collection<Student> result = out.findAllStudents();

        assertThat(result).hasSize(2);
        assertThat(result).contains(POTTER, MALFOY);
    }

    @Test
    void shouldUpdateStudent() {
        when(studentRepository.save(UPD_POTTER)).thenReturn(UPD_POTTER);
        Student result = out.updateStudent(UPD_POTTER);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(UPD_POTTER);
    }

    @Test
    @Disabled
    void shouldReturnNullIfStudentNotFoundWhenUpdateStudent() {
        Student result = out.updateStudent(UPD_POTTER);

        assertThat(result).isNull();
    }

    @Test
    @Disabled
    void shouldDeleteStudent() {
        out.createStudent(POTTER);
        out.deleteStudent(1L);

        assertThat(out.findAllStudents()).hasSize(0);
    }

    @Test
    void shouldReturnStudentsWithSpecificAge() {
        when(studentRepository.findByAge(AGE)).thenReturn(List.of(POTTER, GRANGER));
        Collection<Student> result = out.findStudentsByAge(AGE);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(POTTER, GRANGER);
    }

    @Test
    void shouldReturnStudentsWithAgeBetweenMinAndMax() {
        when(studentRepository.findStudentsByAgeBetween(MIN_AGE, MAX_AGE)).thenReturn(List.of(POTTER, GRANGER));
        Collection<Student> result = out.findStudentsByAgeBetween(MIN_AGE, MAX_AGE);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(POTTER, GRANGER);
    }

    @Test
    void shouldReturnFacultyForStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(POTTER));
        Faculty result = out.getFacultyForStudent(POTTER.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(GRIFFINDOR);
    }

    @Test
    void shouldReturnNumberOfStudents() {
        when(studentRepository.getNumberOfStudents()).thenReturn(NUMBER_OF_STUDENTS);
        Integer result = out.getNumberOfStudents();

        assertThat(result).isEqualTo(NUMBER_OF_STUDENTS);
    }

    @Test
    void shouldReturnAverageAgeOfStudents() {
        when(studentRepository.getAverageAgeOfStudents()).thenReturn(AVERAGE_AGE);
        Double result = out.getAverageAgeOfStudents();

        assertThat(result).isEqualTo(AVERAGE_AGE);
    }

    @Test
    void shouldReturnLastlyCreatedStudents() {
        when(studentRepository.getFiveLastCreatedStudents()).thenReturn(List.of(MALFOY));
        Collection<Student> result = out.getFiveLastCreatedStudents();

        assertThat(result).hasSize(1);
        assertThat(result).contains(MALFOY);
    }

    @Test
    void shouldReturnAllStudentsNameStartsWithACapitalize() {
        when(studentRepository.findAll()).thenReturn(STUDENTS_WITH_A);
        Collection<String> result = out.findAllStudentsNameStartsWithACapitalize();

        assertThat(result).containsAll(NAMES_A_CAPITALIZE);
    }

    @Test
    void shouldReturnAverageAgeWithStreamOfStudents() {
        when(studentRepository.findAll()).thenReturn(STUDENTS_WITH_A);
        Double result = out.getAverageAgeOfStudentsWithStream();

        assertThat(result).isEqualTo(STUDENTS_WITH_A.stream().mapToInt(Student::getAge).average().getAsDouble());
    }
}