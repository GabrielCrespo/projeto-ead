package com.ead.authuser.controller;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.service.UserService;
import com.ead.authuser.validation.UserValidator;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private final UserService userService;

    private final UserValidator userValidator;

    public AuthenticationController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                           @JsonView(UserDto.UserView.RegistrationPost.class) UserDto dto,
                                           Errors errors) {

        LOGGER.debug("POST register userRecordDto received {}", dto);

        userValidator.validate(dto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(dto));
    }

    @GetMapping("/logs")
    public String index() {
        LOGGER.trace("TRACE");
        LOGGER.debug("DEBUG");
        LOGGER.info("INFO");
        LOGGER.warn("WARN");
        LOGGER.error("ERROR");
        return "Logging Spring Boot...";
    }
}
