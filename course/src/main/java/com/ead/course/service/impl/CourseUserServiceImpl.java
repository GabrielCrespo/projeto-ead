package com.ead.course.service.impl;

import com.ead.course.model.Course;
import com.ead.course.model.CourseUser;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseUserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;

    public CourseUserServiceImpl(CourseUserRepository courseUserRepository) {
        this.courseUserRepository = courseUserRepository;
    }

    @Override
    public boolean existsByCourseAndUserId(Course course, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(course, userId);
    }

    @Override
    public CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser) {
        courseUser = courseUserRepository.save(courseUser);
        // send
        return courseUser;
    }
}
