package com.ead.course.repository;

import com.ead.course.model.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {
}
