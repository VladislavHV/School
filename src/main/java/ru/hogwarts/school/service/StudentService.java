package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long schoolId);

    Student updateStudent(Student student);

    Student deleteStudent(Long studentId);

    Collection<Student> getAllStudents();

    Collection<Student> findStudentByAge(int age);
}
