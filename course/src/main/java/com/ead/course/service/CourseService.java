package com.ead.course.service;

import com.ead.course.dto.CourseRecordDto;
import com.ead.course.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(Course course);

    Object save(CourseRecordDto courseRecordDto);

    boolean existsByName(String name);

    List<Course> findAll();

    Optional<Course> findById(UUID courseId);

    Course update(CourseRecordDto courseRecordDto, Course course);
}
