package com.ead.course.dto;

import com.ead.course.model.User;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

public record UserEventRecordDto(UUID userId,
                                 String username,
                                 String email,
                                 String fullname,
                                 String userType,
                                 String userStatus,
                                 String phoneNumber,
                                 String imageUrl,
                                 String actionType) {

    public User toUser() {
        var user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

}
