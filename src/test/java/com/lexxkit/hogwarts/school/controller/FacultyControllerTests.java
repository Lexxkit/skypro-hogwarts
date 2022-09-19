package com.lexxkit.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.repository.FacultyRepository;
import com.lexxkit.hogwarts.school.service.FacultyService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.lexxkit.hogwarts.school.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void getAllFacultiesTest() throws Exception{
        when(facultyRepository.findAll()).thenReturn(List.of(GRIFFINDOR));

        mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getFacultyById() throws Exception {
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(GRIFFINDOR));

        mockMvc.perform(get("/faculties/" + GRIFFINDOR.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GRIFFINDOR.getId()))
                .andExpect(jsonPath("$.name").value(GRIFFINDOR.getName()))
                .andExpect(jsonPath("$.color").value(GRIFFINDOR.getColor()));
    }

    @Test
    void getFacultiesByColor() throws Exception {
        when(facultyRepository.findByColor(COLOR)).thenReturn(List.of(SLYTHERIN));

        mockMvc.perform(get("/faculties")
                        .queryParam("color", COLOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(SLYTHERIN))));
    }

    @Test
    void getFacultiesByColorOrNameIgnoreCase() throws Exception {
        String colorIgnoreCase = SLYTHERIN.getColor().toUpperCase();
        String nameIgnoreCase = GRIFFINDOR.getName().toUpperCase();

        when(facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(nameIgnoreCase, colorIgnoreCase))
                .thenReturn(List.of(SLYTHERIN, GRIFFINDOR));

        mockMvc.perform(get("/faculties")
                        .queryParam("color", colorIgnoreCase)
                        .queryParam("name", nameIgnoreCase)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(SLYTHERIN, GRIFFINDOR))));
    }

    @Test
    void createFacultyTest() throws Exception{
        JSONObject griffindorObject = new JSONObject();
        griffindorObject.put("name", GRIFFINDOR.getName());
        griffindorObject.put("color", GRIFFINDOR.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(GRIFFINDOR);

        mockMvc.perform(post("/faculties")
                        .content(griffindorObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GRIFFINDOR.getId()))
                .andExpect(jsonPath("$.name").value(GRIFFINDOR.getName()))
                .andExpect(jsonPath("$.color").value(GRIFFINDOR.getColor()));
    }

    @Test
    void createFacultyBadRequestTest() throws Exception{
        JSONObject griffindorObject = new JSONObject();
        griffindorObject.put("name", GRIFFINDOR.getName());
        griffindorObject.put("color", GRIFFINDOR.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(null);

        mockMvc.perform(post("/faculties")
                        .content(griffindorObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateFacultyTest() throws Exception {
        JSONObject updGriffindorObject = new JSONObject();
        updGriffindorObject.put("name", UPD_GRIFFINDOR.getName());
        updGriffindorObject.put("color", UPD_GRIFFINDOR.getColor());

        when(facultyRepository.findById(GRIFFINDOR.getId())).thenReturn(Optional.of(GRIFFINDOR));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(UPD_GRIFFINDOR);

        mockMvc.perform(put("/faculties")
                        .content(updGriffindorObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(UPD_GRIFFINDOR.getId()))
                .andExpect(jsonPath("$.name").value(UPD_GRIFFINDOR.getName()))
                .andExpect(jsonPath("$.color").value(UPD_GRIFFINDOR.getColor()));
    }

    @Test
    void deleteFacultyTest() throws Exception{
        doNothing().when(facultyRepository).deleteById(anyLong());

        mockMvc.perform(delete("/faculties/" + GRIFFINDOR.getId()))
                .andExpect(status().isOk());

        verify(facultyRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void getTheLongestFacultyName() throws Exception {
        when(facultyRepository.findAll()).thenReturn(List.of(GRIFFINDOR, SLYTHERIN));

        mockMvc.perform(get("/faculties/longest-name"))
                .andExpect(status().isOk())
                .andExpect(content().string(THE_LONGEST_NAME));

    }
}