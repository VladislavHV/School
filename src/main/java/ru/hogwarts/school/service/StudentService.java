package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long schoolId);

    Student updateStudent(Student student);

    Student deleteStudent(Long studentId);

    Collection<Student> getAllStudents();

    List<Student> getStudentByAge(int age);
}
