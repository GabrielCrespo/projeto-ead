package com.ead.authuser.service.impl;

import com.ead.authuser.model.User;
import com.ead.authuser.model.UserCourse;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.service.UserCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository userCourseRepository;

    public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public boolean existsByUserAndCourseId(User user, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(user, courseId);
    }

    @Override
    public UserCourse save(UserCourse userCourse) {
        return userCourseRepository.save(userCourse);
    }

    @Override
    public boolean existsByCourseId(UUID courseId) {
        return userCourseRepository.existsByCourseId(courseId);
    }

    @Transactional
    @Override
    public void deleteAllByCourseId(UUID courseId) {
        userCourseRepository.deleteAllByCourseId(courseId);
    }
}
