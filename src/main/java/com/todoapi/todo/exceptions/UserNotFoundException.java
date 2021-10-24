package com.todoapi.todo.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("Could not find user with id " + id);
    }
}
