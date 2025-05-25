package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    //Метод позволяющий уменьшить размер фото для визуализации.
    @GetMapping("/students/{id}/photo")
    public ResponseEntity<byte[]> getStudentPhoto(@PathVariable Long id) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty() || studentOpt.get().getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] photo = studentOpt.get().getPhoto();

        if (photo.length > 1 * 200 * 200) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(photo.length);

        return new ResponseEntity<>(photo, headers, HttpStatus.OK);
    }
}
