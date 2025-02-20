package com.ead.course.service.impl;

import com.ead.course.exception.NotFoundExcepetion;
import com.ead.course.model.User;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.UserRepository;
import com.ead.course.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    public UserServiceImpl(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        courseRepository.deleteCourseUserByUser(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExcepetion("Error: User not found."));
    }

}
