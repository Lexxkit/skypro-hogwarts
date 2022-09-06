package com.lexxkit.hogwarts.school.repository;

import com.lexxkit.hogwarts.school.model.Avatar;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}
