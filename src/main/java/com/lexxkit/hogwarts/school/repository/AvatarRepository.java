package com.lexxkit.hogwarts.school.repository;

import com.lexxkit.hogwarts.school.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
