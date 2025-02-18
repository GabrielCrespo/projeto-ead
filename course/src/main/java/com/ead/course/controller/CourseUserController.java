package com.ead.course.controller;

import com.ead.course.dto.SubscriptionRecordDto;
import com.ead.course.model.Course;
import com.ead.course.service.CourseService;
import com.ead.course.service.UserService;
import com.ead.course.specification.SpecificationTemplate;
import jakarta.validation.Valid;
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

    private final CourseService courseService;

    private final UserService userService;

    public CourseUserController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(SpecificationTemplate.UserSpec spec,
                                                      @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                      @PathVariable UUID courseId) {
        courseService.findById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionRecordDto subscriptionRecordDto) {

        Optional<Course> courseOptional = courseService.findById(courseId);
        // Verifications with state transfer
        return ResponseEntity.status(HttpStatus.CREATED).body(" ");

    }

}
