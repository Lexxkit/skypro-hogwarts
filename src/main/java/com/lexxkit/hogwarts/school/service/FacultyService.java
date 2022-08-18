package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long idCounter = 0;

    public Collection<Faculty> findAllFaculties() {
        return faculties.values();
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty createFaculty(Faculty faculty) {
        if (isFacultyInMap(faculty)){
            return null;
        }
        faculty.setId(++idCounter);
        faculties.put(idCounter, faculty);
        return faculty;
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (isFacultyInMap(faculty)) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }


    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    private boolean isFacultyInMap(Faculty faculty) {
        Faculty oldFaculty = faculties.get(faculty.getId());
        return oldFaculty != null;
    }
}
