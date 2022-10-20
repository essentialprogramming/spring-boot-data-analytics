package com.mapper;


import com.base.persistence.entities.User;
import com.model.UserInput;
import com.output.UserJSON;

import java.time.OffsetDateTime;

public class UserMapper {

    public static User inputToUser(UserInput input) {
        return User.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .createdAt(OffsetDateTime.now())
                .build();
    }

    public static UserJSON userToJson(User user) {
        return UserJSON.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }


}
