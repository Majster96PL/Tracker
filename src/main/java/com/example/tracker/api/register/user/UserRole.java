package com.example.tracker.api.register.user;

import java.util.stream.Stream;

public enum UserRole {
    ADMIN, USER;

    public static Stream<UserRole> stream(){
        return Stream.of(UserRole.values());
    }
}