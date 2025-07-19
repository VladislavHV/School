package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Вызван метод createFaculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Вызван метод getFaculty");
        return facultyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Факультет не найден по Id" + id));
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Вызван метод updateFaculty");
        if (facultyRepository.existsById(faculty.getId())) {
            return facultyRepository.save(faculty);
        }
        return null;
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        logger.warn("Вызван метод deleteFaculty");
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            facultyRepository.deleteById(id);
            return faculty.get();
        }
        return null;
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("Вызван метод getAllFaculties");
        return facultyRepository.findAll();
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Вызван метод getFacultiesByColor");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    @Override
    public List<Faculty> findByNameOrColor(@RequestParam String query) {
        logger.info("Вызван метод findByNameOrColor");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }

    @Override
    public List<Student> getStudentByFacultyId(Long id) {
        logger.debug("Вызван метод getStudentByFacultyId");
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        return faculty.getStudents();
    }

}
