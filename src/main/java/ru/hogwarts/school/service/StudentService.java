package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(@PathVariable Student student);

    Student getStudent(@PathVariable Long schoolId);

    Student updateStudent(@RequestBody Student student);

    Student deleteStudent(@PathVariable Long studentId);

    Collection<Student> getAllStudents();

    Collection<Student> findStudentByAge(int age);
}
