package com.ead.course.service;

import com.ead.course.model.Course;
import com.ead.course.model.CourseUser;

import java.util.UUID;

public interface CourseUserService {

    boolean existsByCourseAndUserId(Course course, UUID userId);

    CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser);
}
