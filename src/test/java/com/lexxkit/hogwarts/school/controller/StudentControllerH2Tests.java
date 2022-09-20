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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    void testGetNumberOfStudents() {
        Student student_18 = givenStudentWith("studentName3", 18);
        Student student_25 = givenStudentWith("studentName1", 25);
        Student student_28 = givenStudentWith("studentName2", 28);
        Student student_32 = givenStudentWith("studentName4", 32);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_32);

        thenNumberOfStudentsHasBeenCounted(4);
    }

    @Test
    void testGetAverageAgeOfStudents() {
        Student student_18 = givenStudentWith("studentName3", 18);
        Student student_25 = givenStudentWith("studentName1", 25);
        Student student_28 = givenStudentWith("studentName2", 28);
        Student student_32 = givenStudentWith("studentName4", 32);

        double expectedAverageAge = Stream.of(student_18, student_25, student_28, student_32)
                                            .mapToDouble(Student::getAge).average().orElse(0);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_32);

        thenAverageAgeHasBeenCounted(expectedAverageAge);
    }

    @Test
    void testGetFiveLastCreatedStudents() {
        Student student_1 = givenStudentWith("studentName1", 1);
        Student student_2 = givenStudentWith("studentName2", 2);
        Student student_3 = givenStudentWith("studentName3", 3);
        Student student_4 = givenStudentWith("studentName4", 4);
        Student student_5 = givenStudentWith("studentName5", 5);
        Student student_6 = givenStudentWith("studentName6", 6);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_1);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_2);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_3);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_4);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_5);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_6);

        thenFiveLastlyCreatedStudentsAreFound(student_6, student_5, student_4, student_3, student_2);
    }

    @Test
    void testGetAllStudentsNameStartsWithACapitalize() {
        Student student_1 = givenStudentWith("AstudentName1", 1);
        Student student_2 = givenStudentWith("AstudentName2", 2);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_1);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_2);

        thenCollectionOfCapitalizedNamesStartsWithAFound(student_1, student_2);
    }

    @Test
    void testGetAverageAgeOfStudentsWithStream() {
        Student student_18 = givenStudentWith("studentName3", 18);
        Student student_25 = givenStudentWith("studentName1", 25);
        Student student_28 = givenStudentWith("studentName2", 28);
        Student student_32 = givenStudentWith("studentName4", 32);

        double expectedAverageAge = Stream.of(student_18, student_25, student_28, student_32)
                .mapToDouble(Student::getAge).average().orElse(0);

        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_18);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_25);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_28);
        whenSendingCreateStudentRequest(getUriBuilder().build().toUri(), student_32);

        thenAverageAgeWithStreamHasBeenCounted(expectedAverageAge);
    }

    private void thenAverageAgeWithStreamHasBeenCounted(double expectedAverageAge) {
        URI uri = getUriBuilder().path("/average-age-stream").build().toUri();
        ResponseEntity<Double> response = restTemplate.getForEntity(uri, Double.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(expectedAverageAge);
    }

    private void thenCollectionOfCapitalizedNamesStartsWithAFound(Student... students) {
        URI uri = getUriBuilder().path("/namewithA").build().toUri();

        ResponseEntity<Collection<String>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<String>>() {}
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Collection<String> actualResult = response.getBody();
        assertThat(actualResult).containsAll(
                Stream.of(students).map(s -> s.getName().toUpperCase()).collect(Collectors.toList())
        );
    }

    private void thenFiveLastlyCreatedStudentsAreFound(Student... students) {
        URI uri = getUriBuilder().path("/five-last").build().toUri();

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

    private void thenAverageAgeHasBeenCounted(double expectedAverageAge) {
        URI uri = getUriBuilder().path("/average-age").build().toUri();
        ResponseEntity<Double> response = restTemplate.getForEntity(uri, Double.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(expectedAverageAge);
    }

    private void thenNumberOfStudentsHasBeenCounted(int numberOfStudents) {
        URI uri = getUriBuilder().path("/number").build().toUri();
        ResponseEntity<Integer> response = restTemplate.getForEntity(uri, Integer.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(numberOfStudents);
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
