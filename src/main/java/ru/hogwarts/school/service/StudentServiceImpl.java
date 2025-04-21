package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long id = 1;

    @Override
    public Student createStudent(@PathVariable Student student) {
        student.setId(id++);
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudent(@PathVariable Long schoolId) {
        return studentMap.get(schoolId);
    }

    @Override
    public Student updateStudent(@RequestBody Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }

    @Override
    public Student deleteStudent(@PathVariable Long studentId) {
        return studentMap.remove(studentId);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentMap.values();
    }

    @Override
    public Collection<Student> findStudentByAge(int age) {
        return studentMap.values()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
