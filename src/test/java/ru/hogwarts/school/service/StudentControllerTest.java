package ru.hogwarts.school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //Баг с IDEA или костыль ничего страшного, тесты работают!!!
    private StudentServiceImpl studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Collection<Student> getMockStudents() {
        return Arrays.asList(
                new Student(19, 1L, "Petr"),
                new Student(20, 2L, "Sergey"),
                new Student(35, 3L, "Anatoly"),
                new Student(28, 4L, "Dmitry"),
                new Student(31, 5L, "Popov"),
                new Student(18, 6L, "Andrey")
        );
    }

    @Test
    void createStudentTest() throws Exception {
        Student student = new Student(20, 1L, "Harry");

        Mockito.when(studentService.createStudent(Mockito.any())).thenReturn(student);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry"));
    }

    @Test
    void getStudentTest() throws Exception {
        Student student = new Student(20, 1L, "Hermione");

        Mockito.when(studentService.getStudent(2L)).thenReturn(student);

        mockMvc.perform(get("/student/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hermione"));
    }

    @Test
    void testPrintParallelEndpointReturnsOk() throws Exception {
        Mockito.when(studentService.getAllStudents()).thenReturn(getMockStudents());

        mockMvc.perform(get("/student/students/print-parallel"))
                .andExpect(status().isOk());
    }

    @Test
    void testPrintSynchronizedEndpointReturnsOk() throws Exception {
        Mockito.when(studentService.getAllStudents()).thenReturn(getMockStudents());

        mockMvc.perform(get("/student/students/print-synchronized"))
                .andExpect(status().isOk());
    }

}
