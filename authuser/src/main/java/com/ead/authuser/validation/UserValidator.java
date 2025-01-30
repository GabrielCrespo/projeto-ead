package com.ead.authuser.validation;

import com.ead.authuser.controller.AuthenticationController;
import com.ead.authuser.dto.UserDto;
import com.ead.authuser.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private final Validator validator;

    private final UserService userService;

    public UserValidator(@Qualifier("defaultValidator") Validator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        validator.validate(target, errors);
        if (!errors.hasErrors()) {
            validateUsername(userDto, errors);
            validateEmail(userDto, errors);
        }
    }

    private void validateUsername(UserDto userDto, Errors errors) {
        if (userService.existsByUserName(userDto.username())) {
            errors.rejectValue("username", "usernameConflict", "Username is already taken!");
            LOGGER.error("Error validation username: {}", userDto.username());
        }
    }

    private void validateEmail(UserDto userDto, Errors errors) {
        if (userService.existsByEmail(userDto.email())) {
            errors.rejectValue("email", "emailConflict", "Email is already taken!");
            LOGGER.error("Error validation email: {}", userDto.email());
        }
    }
}
