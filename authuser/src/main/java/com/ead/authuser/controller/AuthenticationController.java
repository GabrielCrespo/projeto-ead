package com.ead.authuser.controller;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                           @JsonView(UserDto.UserView.RegistrationPost.class) UserDto dto) {

        LOGGER.debug("POST register userRecordDto received {}", dto);

        if (userService.existsByUserName(dto.username())) {
            LOGGER.warn("Username {} is Already Taken", dto.username());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken!");
        }

        if (userService.existsByEmail(dto.email())) {
            LOGGER.warn("Email {} is Already Taken", dto.username());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken!");
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
