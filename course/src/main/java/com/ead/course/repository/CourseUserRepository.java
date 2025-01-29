package com.ead.course.repository;

import com.ead.course.model.Course;
import com.ead.course.model.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    boolean existsByCourseAndUserId(Course course, UUID userId);

}
