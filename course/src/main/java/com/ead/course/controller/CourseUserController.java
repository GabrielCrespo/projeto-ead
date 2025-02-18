package com.ead.course.controller;

import com.ead.course.dto.SubscriptionRecordDto;
import com.ead.course.model.Course;
import com.ead.course.service.CourseService;
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

    public CourseUserController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                      @PathVariable UUID courseId) {
        courseService.findById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(" "); // Refactor
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionRecordDto subscriptionRecordDto) {

        Optional<Course> courseOptional = courseService.findById(courseId);
        // Verifications with state transfer
        return ResponseEntity.status(HttpStatus.CREATED).body(" ");

    }

}
