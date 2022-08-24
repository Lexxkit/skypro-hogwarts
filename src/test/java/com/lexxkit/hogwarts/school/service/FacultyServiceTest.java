package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.repository.FacultyRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.lexxkit.hogwarts.school.service.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyService out;

    @Test
    void shouldCreateNewFaculty() {
        when(facultyRepository.save(GRIFFINDOR)).thenReturn(GRIFFINDOR);
        Faculty result = out.createFaculty(GRIFFINDOR);

        assertThat(result).isEqualTo(GRIFFINDOR);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Disabled
    @Test
    void shouldReturnNullWhenCreateTheSameFaculty() {
        out.createFaculty(GRIFFINDOR);
        Faculty result = out.createFaculty(GRIFFINDOR);

        assertThat(result).isNull();
    }

    @Test
    void shouldFindFacultyById() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(GRIFFINDOR));
        Faculty result = out.findFaculty(1L);

        assertThat(result).isEqualTo(GRIFFINDOR);
    }

    @Test
    void shouldReturnAllFaculties() {
        when(facultyRepository.findAll()).thenReturn(List.of(GRIFFINDOR, SLYTHERIN));
        Collection<Faculty> result = out.findAllFaculties();

        assertThat(result).hasSize(2);
        assertThat(result).contains(GRIFFINDOR, SLYTHERIN);
    }

    @Test
    void shouldUpdateFaculty() {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(UPD_GRIFFINDOR);

        Faculty result = out.updateFaculty(UPD_GRIFFINDOR);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(UPD_GRIFFINDOR);
    }

    @Disabled
    @Test
    void shouldReturnNullIfFacultyNotFoundWhenUpdateFaculty() {
        Faculty result = out.updateFaculty(UPD_GRIFFINDOR);

        assertThat(result).isNull();
    }

    @Disabled
    @Test
    void shouldDeleteFaculty() {
        out.createFaculty(GRIFFINDOR);
        out.deleteFaculty(1L);

        assertThat(out.findAllFaculties()).hasSize(0);
    }

    @Test
    void shouldReturnFacultiesWithSpecificColor() {
        when(facultyRepository.findByColor(COLOR)).thenReturn(List.of(SLYTHERIN));
        Collection<Faculty> result = out.findFacultyByColor(COLOR);

        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyInAnyOrder(SLYTHERIN);
    }

    @Test
    void shouldReturnFacultiesWithNameOrColor() {
        when(facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(NAME, COLOR)).thenReturn(List.of(SLYTHERIN, GRIFFINDOR));
        Collection<Faculty> result = out.findFacultyByNameOrColor(NAME, COLOR);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(SLYTHERIN, GRIFFINDOR);
    }

}