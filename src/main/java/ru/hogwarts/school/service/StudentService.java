package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long schoolId);

    Student updateStudent(Student student);

    Student deleteStudent(Long studentId);

    Collection<Student> getAllStudents();

    List<Student> getStudentByAge(int age);

    List<Student> getStudentByAgeRange(int min, int max);

    Faculty getFacultyByStudentId(Long studentId);

    Optional<Student> findById(Long id);
}
