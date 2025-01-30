package com.ead.course.service.impl;

import com.ead.course.client.AuthUserClient;
import com.ead.course.model.Course;
import com.ead.course.model.CourseUser;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;

    private final AuthUserClient authUserClient;

    public CourseUserServiceImpl(CourseUserRepository courseUserRepository, AuthUserClient authUserClient) {
        this.courseUserRepository = courseUserRepository;
        this.authUserClient = authUserClient;
    }

    @Override
    public boolean existsByCourseAndUserId(Course course, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(course, userId);
    }

    @Transactional
    @Override
    public CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser) {
        courseUser = courseUserRepository.save(courseUser);
        authUserClient.postSubscriptionUserInCourse(courseUser.getCourse().getCourseId(), courseUser.getUserId());
        return courseUser;
    }
}
