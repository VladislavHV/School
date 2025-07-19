package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeWorkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //Помечаются красным, ошибок не вызывает!!!
    private StudentServiceImpl studentService;

    @MockBean //Помечаются красным, ошибок не вызывает!!!
    private AvatarServiceImpl avatarService;

    @Test
    void testCountStudent() throws Exception {
        Mockito.when(studentService.getStudentCount()).thenReturn(100L);

        mockMvc.perform(get("/student/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
    }

    @Test
    void testAverageAge() throws Exception {
        Mockito.when(studentService.getAverageStudentAge()).thenReturn(17.5);

        mockMvc.perform(get("/student/average-age"))
                .andExpect(status().isOk())
                .andExpect(content().string("17.5"));
    }

    @Test
    void testLastFiveStudents() throws Exception {
        List<Student> lastStudents = List.of(
                new Student(11, 101L, "Slava"),
                new Student(12, 102L, "Petr")
        );
        Mockito.when(studentService.getLastStudents()).thenReturn(lastStudents);

        mockMvc.perform(get("/student/last-five"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Slava"))
                .andExpect(jsonPath("$[1].name").value("Petr"));
    }

    @Test
    void testAvatarPagination() throws Exception {
        List<Avatar> avatars = List.of(new Avatar(), new Avatar());
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(avatarService.getAllAvatars(pageable))
                .thenReturn(new PageImpl<>(avatars, pageable, avatars.size()));

        mockMvc.perform(get("/avatars?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

}
