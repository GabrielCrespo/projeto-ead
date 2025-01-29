package com.ead.course.controller;

import com.ead.course.client.AuthUserClient;
import com.ead.course.dto.SubscriptionRecordDto;
import com.ead.course.dto.UserRecordDto;
import com.ead.course.model.Course;
import com.ead.course.model.CourseUser;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
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
public class CourseUserController {

    private final AuthUserClient authUserClient;

    private final CourseService courseService;

    private final CourseUserService courseUserService;

    public CourseUserController(AuthUserClient authUserClient, CourseService courseService, CourseUserService courseUserService) {
        this.authUserClient = authUserClient;
        this.courseService = courseService;
        this.courseUserService = courseUserService;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserRecordDto>> getAllUsersByCourse(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                   @PathVariable UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(pageable, courseId));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionRecordDto subscriptionRecordDto) {

        Optional<Course> courseOptional = courseService.findById(courseId);

        if (courseUserService.existsByCourseAndUserId(courseOptional.get(), subscriptionRecordDto.userId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists!");
        }

        // User verification

        CourseUser courseUser = courseOptional.get().convertToCourseUser(subscriptionRecordDto.userId());
        courseUser = courseUserService.saveAndSendSubscriptionUserInCourse(courseUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUser);

    }

}
