package com.todoapi.todo.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Integer id) {
        super("Could not find task with id " + id);
    }
}