package com.lexxkit.hogwarts.school.service;

import com.lexxkit.hogwarts.school.model.Faculty;
import com.lexxkit.hogwarts.school.model.Student;
import com.lexxkit.hogwarts.school.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Collection<Faculty> findAllFaculties() {
        logger.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty by id: {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }


    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty by id: {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        logger.info("Was invoked method for find faculties by color");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findFacultyByNameOrColor(String name, String color) {
        logger.info("Was invoked method for find faculties by name or color");
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentsForFaculty(long id) {
        logger.info("Was invoked method for get all students for faculty with id: {}", id);
        Faculty faculty = findFaculty(id);
        if (faculty == null) {
            logger.warn("Wrong id: {}. There is no faculty with such id", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no faculty with id: " + id);
        }
        return faculty.getStudents();
    }
}
