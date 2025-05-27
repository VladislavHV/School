package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //Не обращать внимание, просто костыль, тесты проходят проверку.
    private FacultyServiceImpl facultyService;

    @Test
    void testGetFaculty() throws Exception {
        Faculty faculty = new Faculty("Blue", 1L, "IT");
        Mockito.when(facultyService.getFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("IT"));
    }

    @Test
    void testUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty("White", 1L, "Physics");
        Mockito.when(facultyService.updateFaculty(Mockito.any())).thenReturn(faculty);

        mockMvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Physics\",\"color\":\"White\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Physics"));
    }

    @Test
    void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty("Green", null, "Math");
        Mockito.when(facultyService.createFaculty(Mockito.any())).thenReturn(new Faculty("Green", 1L, "Math"));

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Math\",\"color\":\"Green\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math"));
    }

    @Test
    void testDeleteFaculty() throws Exception {
        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllFaculties() throws Exception {
        Mockito.when(facultyService.getAllFaculties()).thenReturn(List.of(new Faculty("Blue", 1L, "IT")));
        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("IT"));
    }

}
