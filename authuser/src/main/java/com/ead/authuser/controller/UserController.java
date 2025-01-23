package com.ead.authuser.controller;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.exceptions.GlobalExceptionHandler;
import com.ead.authuser.model.User;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(SpecificationTemplate.UserSpec spec, Pageable pageable) {
        Page<User> userPage = userService.findAll(spec, pageable);

        if (!userPage.isEmpty()) {
            for (User user : userPage.toList()) {
                user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                        .getOneUser(user.getUserId())).withSelfRel()
                );
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable("userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(userId).get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") UUID userId) {
        LOGGER.debug("DELETE deleteUser userId received {}", userId);
        userService.delete(userService.findById(userId).get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("userId") UUID userId,
            @RequestBody @JsonView(UserDto.UserView.UserPut.class)
            @Validated(UserDto.UserView.UserPut.class) UserDto dto) {
        LOGGER.debug("PUT updateUser userDto received {}", dto);
        var user = userService.findById(userId).get();
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(dto, user));

    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable("userId") UUID userId,
            @RequestBody
            @JsonView(UserDto.UserView.PasswordPut.class)
            @Validated(UserDto.UserView.PasswordPut.class) UserDto dto) {

        LOGGER.debug("PUT updatePassword userId {}", userId);
        var user = userService.findById(userId).get();

        if (!user.getPassword().equals(dto.oldPassword())) {
            LOGGER.warn("Mismatched old password! userId {}", userId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched password");
        }

        userService.updatePassword(dto, user);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated succesfully!");

    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<?> updateImage(
            @PathVariable("userId") UUID userId,
            @RequestBody @JsonView(UserDto.UserView.ImagePut.class)
            @Validated(UserDto.UserView.ImagePut.class) UserDto dto) {

        LOGGER.debug("PUT updateImage userDto {}", dto);
        var user = userService.findById(userId).get();
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateImage(dto, user));

    }

}
