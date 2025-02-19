package com.ead.course.service;

import com.ead.course.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    User save(User user);
}
