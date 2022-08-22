package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.repository.FacultyRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static com.lexxkit.hogwarts.school.service.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyService out;

    @Test
    void shouldCreateNewFaculty() {
        Faculty result = out.createFaculty(GRIFFINDOR);

        assertThat(result).isEqualTo(GRIFFINDOR);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(out.findAllFaculties()).hasSize(1);
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
        out.createFaculty(GRIFFINDOR);
        out.createFaculty(SLYTHERIN);
        Faculty result = out.findFaculty(1L);

        assertThat(result).isEqualTo(GRIFFINDOR);
    }

    @Test
    void shouldReturnAllFaculties() {
        out.createFaculty(GRIFFINDOR);
        out.createFaculty(SLYTHERIN);
        Collection<Faculty> result = out.findAllFaculties();

        assertThat(result).hasSize(2);
        assertThat(result).contains(GRIFFINDOR, SLYTHERIN);
    }

    @Test
    void shouldUpdateFaculty() {
        out.createFaculty(GRIFFINDOR);
        Faculty result = out.updateFaculty(UPD_GRIFFINDOR);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(UPD_GRIFFINDOR);
    }

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
        out.createFaculty(GRIFFINDOR);
        out.createFaculty(SLYTHERIN);
        Collection<Faculty> result = out.findFacultyByColor(COLOR);

        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyInAnyOrder(SLYTHERIN);
    }

}