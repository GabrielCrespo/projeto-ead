package com.ead.authuser.service;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(UUID userId);

    void delete(User user);

    User register(UserDto dto);

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    User update(UserDto userDto, User user);

    User updatePassword(UserDto userDto, User user);

    User updateImage(UserDto userDto, User user);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

}
