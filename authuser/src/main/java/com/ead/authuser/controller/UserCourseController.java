package com.ead.authuser.controller;

import com.ead.authuser.client.CourseClient;
import com.ead.authuser.dto.CourseRecordDto;
import com.ead.authuser.dto.UserCourseDto;
import com.ead.authuser.model.User;
import com.ead.authuser.model.UserCourse;
import com.ead.authuser.service.UserCourseService;
import com.ead.authuser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UserCourseController {

    private final CourseClient courseClient;

    private final UserService userService;

    private final UserCourseService userCourseService;

    public UserCourseController(CourseClient courseClient, UserService userService, UserCourseService userCourseService) {
        this.courseClient = courseClient;
        this.userService = userService;
        this.userCourseService = userCourseService;
    }

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseRecordDto>> getAllCoursesByUsers(
            @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));

    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID userId,
                                                               @RequestBody @Valid UserCourseDto userCourseDto) {

        Optional<User> userOptional = userService.findById(userId);

        if (userCourseService.existsByUserAndCourseId(userOptional.get(), userCourseDto.courseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists!");
        }

        UserCourse userCourse = userOptional.get().convertToUserCourse(userCourseDto.courseId());
        userCourse = userCourseService.save(userCourse);

        return ResponseEntity.status(HttpStatus.OK).body(userCourse);
    }

}
