package com.todoapi.todo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.todoapi.todo.domain.*;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    
}
