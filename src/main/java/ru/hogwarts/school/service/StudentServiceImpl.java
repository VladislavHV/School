package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public Student createStudent(Student student) {
        logger.info("Вызван метод createStudent");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long studentId) {
        logger.info("Вызван метод getStudent");
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("Вызван метод updateStudent");
        if (studentRepository.existsById(student.getId())) {
            return studentRepository.save(student);
        }
        return null;
    }

    @Override
    public Student deleteStudent(Long studentId) {
        logger.warn("Вызван метод deleteStudent");
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            studentRepository.deleteById(studentId);
            return student.get();
        }
        return null;
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Вызван метод getAllStudents");
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getStudentByAge(int age) {
        logger.info("Вызван метод getStudentByAge");
        return studentRepository.findStudentAllByAge(age);
    }

    @Override
    public List<Student> getStudentByAgeRange(int min, int max) {
        logger.info("Вызван метод getStudentByAgeRange");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudentId(Long studentId) {
        logger.info("Вызван метод getFacultyByStudentId");
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getFaculty();
    }

    @Override
    public Optional<Student> findById(Long id) {
        logger.info("Вызван метод findById");
        return studentRepository.findById(id);
    }

    @Override
    public Long getStudentCount() {
        logger.info("Вызван метод getStudentCount");
        return studentRepository.getStudentCount();
    }

    @Override
    public Double getAverageStudentAge() {
        logger.info("Вызван метод getAverageStudentAge");
        return studentRepository.getAverageStudentAge();
    }

    @Override
    public List<Student> getLastStudents() {
        logger.debug("Вызван метод getLastStudents");
        return studentRepository.findTop5ByOrderIdDesc();
    }

}


