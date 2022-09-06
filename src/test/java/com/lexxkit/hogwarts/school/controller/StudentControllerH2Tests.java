package com.lexxkit.hogwarts.school.controller;

import com.lexxkit.hogwarts.school.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerH2Tests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateStudent() {
        Student student = givenStudentWith("studentName", 25);
        ResponseEntity<Student> response = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(response);
    }

    @Test
    void testGetStudentById() {
        Student student = givenStudentWith("studentName", 25);

        ResponseEntity<Student> createResponse = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);

        thenStudentHasBeenCreated(createResponse);

        Student createdStudent = createResponse.getBody();
        thenStudentWithIdHasBeenFound(createdStudent.getId(), createdStudent);
    }

    @Test
    void testFindByAge() {
        Student student_18 = givenStudentWith("studentName3", 18);
        Student student_25 = givenStudentWith("studentName1", 25);
        Student student_28 = givenStudentWith("studentName2", 28);
        Student student_32 = givenStudentWith("studentName4", 32);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_32);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "25");
        thenStudentsAreFoundByCriteria(queryParams, student_25);
    }

    @Test
    void testFindByAgeBetween() {
        Student student_18 = givenStudentWith("studentName3", 18);
        Student student_25 = givenStudentWith("studentName1", 25);
        Student student_28 = givenStudentWith("studentName2", 28);
        Student student_32 = givenStudentWith("studentName4", 32);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_32);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("minAge", "20");
        queryParams.add("maxAge", "30");
        thenStudentsAreFoundByCriteria(queryParams, student_25, student_28);
    }

    @Test
    void testUpdateStudent() {
        Student student = givenStudentWith("studentName", 25);

        ResponseEntity<Student> createResponse = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(createResponse);
        Student createdStudent = createResponse.getBody();

        whenUpdatingStudent(createdStudent, 32, "newName");
        thenStudentHasBeenUpdated(createdStudent, 32, "newName");
    }

    @Test
    void testDeleteStudent() {
        Student student = givenStudentWith("studentName", 25);

        ResponseEntity<Student> createResponse = whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(createResponse);
        Student createdStudent = createResponse.getBody();

        whenDeletingStudent(createdStudent);
        thenStudentNotFound(createdStudent);
    }

    private void whenDeletingStudent(Student createdStudent) {
        restTemplate.delete(getUriBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri());
    }

    private void thenStudentNotFound(Student createdStudent) {
        URI uri = getUriBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student> emptyResp = restTemplate.getForEntity(uri, Student.class);

        assertThat(emptyResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void whenUpdatingStudent(Student createdStudent, int newAge, String newName) {
        createdStudent.setAge(newAge);
        createdStudent.setName(newName);

        restTemplate.put(getUriBuilder().build().toUri(), createdStudent);
    }

    private void thenStudentHasBeenUpdated(Student createdStudent, int newAge, String newName) {
        URI uri = getUriBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student> updatedStudentResp = restTemplate.getForEntity(uri, Student.class);

        assertThat(updatedStudentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedStudentResp.getBody()).isNotNull();
        assertThat(updatedStudentResp.getBody().getAge()).isEqualTo(newAge);
        assertThat(updatedStudentResp.getBody().getName()).isEqualTo(newName);
    }

    private void resetIds(Collection<Student> students) {
        // method removes id's for easier comparing of entities (equals method uses id for equality)
        students.forEach(it -> it.setId(null));
    }

    private void thenStudentsAreFoundByCriteria(MultiValueMap<String, String> queryParams, Student... students) {
        URI uri = getUriBuilder().queryParams(queryParams).build().toUri();

        ResponseEntity<Collection<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,     //used for POST, put RequestBody here
                new ParameterizedTypeReference<Collection<Student>>() {}   // this provide us with ResponseEntity with desired collection
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Collection<Student> actualResult = response.getBody();
        resetIds(actualResult);
        assertThat(actualResult).containsExactlyInAnyOrder(students);
    }

    private void thenStudentWithIdHasBeenFound(Long studentId, Student student) {
        URI uri = getUriBuilder().path("/{id}").buildAndExpand(studentId).toUri();
        ResponseEntity<Student> response = restTemplate.getForEntity(uri, Student.class);

        assertThat(response.getBody()).isEqualTo(student);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private Student givenStudentWith(String name, int age) {
        return new Student(name, age);
    }

    private ResponseEntity<Student> whenSendingCreateStudentRequest(URI uri, Student student) {
        return restTemplate.postForEntity(uri, student, Student.class);
    }

    private void thenStudentHasBeenCreated(ResponseEntity<Student> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/students");
    }
}
