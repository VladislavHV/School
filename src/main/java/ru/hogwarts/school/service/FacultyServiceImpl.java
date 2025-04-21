package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long idCounter = 1;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(idCounter++);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        return facultyMap.get(id);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        return facultyMap.remove(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return facultyMap.values();
    }

    @Override
    public Collection<Faculty> findFacultyFilter(String color) {
        return facultyMap.values()
                .stream()
                .filter(faculty -> faculty.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

}
