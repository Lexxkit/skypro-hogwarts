package com.lexxkit.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.lexxkit.hogwarts.school.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllStudent() throws Exception {
        assertThat(this.restTemplate.getForObject(BASE_URL + port + "/students", String.class)).isNotNull();
    }

    @Test
    void testGetAllStudentWithAge() throws Exception {
        assertThat(this.restTemplate.getForObject(BASE_URL + port + "/students?age=" + AGE, String.class))
                .isNotNull()
                .containsAnyOf("Ron");

    }

    @Test
    void testGetAllStudentWithAgeBetween() throws Exception {
        assertThat(this.restTemplate.getForObject(
                BASE_URL + port + "/students?minAge=" + MIN_AGE + "&maxAge=" + MAX_AGE, String.class))
                .isNotNull()
                .containsAnyOf("Ron", "Hermiona");
    }

    @Test
    void testGetStudentById() throws Exception {
        assertThat(this.restTemplate.getForObject(
                BASE_URL + port + "/students/2", String.class))
                .isNotNull()
                .containsAnyOf("Potter");
    }

    @Test
    void testGetFacultyByStudentId() throws Exception {
        assertThat(this.restTemplate.getForObject(
                BASE_URL + port + "/students/2/faculty", String.class))
                .isNotNull()
                .containsAnyOf(GRIFFINDOR.getName());
    }

    @Test
    void testPostStudent() throws Exception {
        assertThat(this.restTemplate.postForObject(BASE_URL + port + "/students", TEST_STUDENT, String.class))
                .isNotNull()
                .containsAnyOf("Test student");
    }
}
