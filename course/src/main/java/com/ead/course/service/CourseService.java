package com.ead.course.service;

import com.ead.course.dto.CourseRecordDto;
import com.ead.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(Course course);

    Object save(CourseRecordDto courseRecordDto);

    boolean existsByName(String name);

    Page<Course> findAll(Specification<Course> spec, Pageable pageable);

    Optional<Course> findById(UUID courseId);

    Course update(CourseRecordDto courseRecordDto, Course course);
}
