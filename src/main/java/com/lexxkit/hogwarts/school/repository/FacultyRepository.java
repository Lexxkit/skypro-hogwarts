package com.lexxkit.hogwarts.school.repository;

import com.lexxkit.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
