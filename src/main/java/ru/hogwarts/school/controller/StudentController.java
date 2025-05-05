package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping //Добавить объект
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}") //Проверить объекты
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping //Изменить объект
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}") //Удалить объект
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping //Посмотреть все объекты
    public Collection<Student> getAllStudent() {
        return studentService.getAllStudents();
    }

    @GetMapping("/age/{age}") //Посмотреть объекты по цвету
    public Collection<Student> getStudentsByAge(@PathVariable int age) {
        return studentService.getStudentByAge(age);
    }

    @GetMapping("/students/by-age-range")
    public List<Student> getStudentByAgeRange(@RequestParam int min, @RequestParam int max) {
        return studentService.getStudentByAgeRange(min, max);
    }

    @GetMapping("/students/{id}/faculty")
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        return studentService.getFacultyByStudentId(id);
    }
}
