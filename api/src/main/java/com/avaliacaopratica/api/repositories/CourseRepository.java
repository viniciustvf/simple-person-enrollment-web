package com.avaliacaopratica.api.repositories;

import com.avaliacaopratica.api.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
