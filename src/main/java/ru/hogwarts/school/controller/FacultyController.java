package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    @Autowired
    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping //Добавить объект
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}") //Проверить объекты
    public Faculty getFaculty(
            @Parameter(description = "ID факультета", example = "1")
            @PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @PutMapping //Изменить объект
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}") //Удалить объект
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping //Посмотреть все объекты
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/color/{color}") //Посмотреть объекты по цвету
    public Collection<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/faculties/search")
    public List<Faculty> findByNameOrColor(@RequestParam String query) {
        return facultyService.findByNameOrColor(query);
    }

    @GetMapping("/faculties/{id}/students")
    public List<Student> getStudentOfFaculty(@PathVariable Long id) {
        return facultyService.getStudentByFacultyId(id);
    }

}
