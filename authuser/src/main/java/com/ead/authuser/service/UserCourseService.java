package com.ead.authuser.service;

import com.ead.authuser.model.User;
import com.ead.authuser.model.UserCourse;

import java.util.UUID;

public interface UserCourseService {

    boolean existsByUserAndCourseId(User user, UUID courseId);

    UserCourse save(UserCourse userCourse);

    boolean existsByCourseId(UUID courseId);

    void deleteAllByCourseId(UUID courseId);
}
