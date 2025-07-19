package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateFaculty() {
        Faculty faculty = new Faculty("Blue", null, "IT");
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", faculty, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetFaculty() {
        Faculty faculty = restTemplate.getForObject("/faculty/1", Faculty.class);
        assertNotNull(faculty);
        assertEquals(null, faculty.getId());
    }

    @Test
    void testUpdateFaculty() {
        Faculty faculty = new Faculty("Red", 1L, "Math");
        restTemplate.put("/faculty", faculty);
    }

    @Test
    void testDeleteFaculty() {
        restTemplate.delete("/faculty/1");
    }

    @Test
    void testGetaAllFaculty() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty", Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
