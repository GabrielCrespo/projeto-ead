package com.ead.authuser.service.impl;

import com.ead.authuser.client.CourseClient;
import com.ead.authuser.dto.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.exceptions.NotFoundException;
import com.ead.authuser.model.User;
import com.ead.authuser.model.UserCourse;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserCourseRepository userCourseRepository;

    private final CourseClient courseClient;

    public UserServiceImpl(
            UserRepository userRepository,
            UserCourseRepository userCourseRepository,
            CourseClient courseClient) {
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
        this.courseClient = courseClient;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new NotFoundException("Error: User not found");
        return userOptional;
    }

    @Transactional
    @Override
    public void delete(User user) {

        boolean deleteUserCourseInCourse = false;

        List<UserCourse> userCourses = userCourseRepository.findAllUserCourseIntoUser(user.getUserId());

        if (!userCourses.isEmpty()) {
            userCourseRepository.deleteAll(userCourses);
            deleteUserCourseInCourse = true;
        }

        userRepository.delete(user);

        if (deleteUserCourseInCourse) {
            courseClient.deleteUserCourseInCourse(user.getUserId());
        }
    }

    @Override
    public User register(UserDto dto) {

        var user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.USER);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User update(UserDto userDto, User user) {
        user.setFullName(userDto.fullName());
        user.setPhoneNumber(userDto.phoneNumber());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(user);

    }

    @Override
    public User updatePassword(UserDto userDto, User user) {
        user.setPassword(userDto.password());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(user);
    }

    @Override
    public User updateImage(UserDto userDto, User user) {

        user.setImgUrl(userDto.imageUrl());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public User registerInstructor(User user) {
        user.setUserType(UserType.INSTRUCTOR);
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(user);
    }
}
