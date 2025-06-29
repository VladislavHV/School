package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //Не обращать внимание, просто костыль, тесты проходят проверку.
    private StudentServiceImpl studentService;

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student(27, 1L, "Иван");
        Mockito.when(studentService.getStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Иван"));
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student(19, null, "Петр");
        Mockito.when(studentService.createStudent(Mockito.any())).thenReturn(new Student(19, 1L, "Петр"));

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Петр\",\"age\":19,\"facultyId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Петр"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student(18, 1L, "Алексей");
        Mockito.when(studentService.updateStudent(Mockito.any())).thenReturn(student);

        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Алексей\",\"age\":18,\"facultyId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Алексей"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllStudents() throws Exception {
        Mockito.when(studentService.getAllStudents()).thenReturn(List.of(new Student(18, 1L, "Иван")));
        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Иван"));
    }

}
