package com.ead.course.validation;

import com.ead.course.dto.CourseRecordDto;
import com.ead.course.enums.UserType;
import com.ead.course.service.CourseService;
import com.ead.course.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger(CourseValidator.class);

    private final Validator validator;

    private final CourseService courseService;

    private final UserService userService;

    public CourseValidator(Validator validator, CourseService courseService, UserService userService) {
        this.validator = validator;
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object object, Errors errors) {
        CourseRecordDto courseRecordDto = (CourseRecordDto) object;
        validator.validate(object, errors);

        if (!errors.hasErrors()) {
            validateCourseName(courseRecordDto, errors);
            validateUserInstructor(courseRecordDto.userInstructor(), errors);
        }
    }

    private void validateCourseName(CourseRecordDto courseRecordDto, Errors errors) {
        if (courseService.existsByName(courseRecordDto.name())) {
            errors.rejectValue("name", "courseNameConflict", "Course Name is Already Taken!");
            LOGGER.error("Error validation courseName: {}", courseRecordDto.name());
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        var user = userService.findById(userInstructor);
        if (UserType.STUDENT.toString().equals(user.getUserType())
                || UserType.USER.toString().equals(user.getUserType())) {
            errors.rejectValue("userInstructor", "userInstructorError", "User must be INSTRUCTOR or ADMIN.");
            LOGGER.error("Error validation userInstructor: {}", userInstructor);
        }
    }
}
