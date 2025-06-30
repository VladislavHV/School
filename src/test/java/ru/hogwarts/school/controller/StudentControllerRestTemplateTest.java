package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateStudent() {
        Student student = new Student(20, 1L, "Иван");
        ResponseEntity<Student> response = restTemplate.postForEntity("/student", student, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Иван", response.getBody().getName());
    }

    @Test
    void testGetStudent() {
        Student student = restTemplate.getForObject("/student/1", Student.class);
        assertNotNull(student);
        assertEquals(1L, student.getId());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student(31, 4L, "Роман");
        restTemplate.put("/student", student);
    }

    @Test
    void testDeleteStudent() {
        restTemplate.delete("/student/4");
    }

    @Test
    void testGetAllStudent() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student", Student[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
