package com.ead.authuser.controller;

import com.ead.authuser.dto.InstructorDto;
import com.ead.authuser.model.User;
import com.ead.authuser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final UserService userService;

    public InstructorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto) {
        Optional<User> userOptional = userService.findById(instructorDto.userId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerInstructor(userOptional.get()));
    }

}
