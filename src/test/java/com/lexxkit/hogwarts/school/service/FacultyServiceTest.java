package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.lexxkit.hogwarts.school.service.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class FacultyServiceTest {
    private FacultyService out = new FacultyService();

    @Test
    void shouldCreateNewFaculty() {
        Faculty result = out.createFaculty(GRIFFINDOR);

        assertThat(result).isEqualTo(GRIFFINDOR);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(out.findAllFaculties()).hasSize(1);
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

        assertThat(result).isEqualTo(UPD_GRIFFINDOR);
    }

    @Test
    void shouldDeleteFaculty() {
        out.createFaculty(GRIFFINDOR);
        Faculty result = out.deleteFaculty(1L);

        assertThat(result).isEqualTo(GRIFFINDOR);
        assertThat(out.findAllFaculties()).hasSize(0);
    }

}