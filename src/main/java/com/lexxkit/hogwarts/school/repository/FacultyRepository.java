package com.lexxkit.hogwarts.school.repository;

import com.lexxkit.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findFacultyByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
