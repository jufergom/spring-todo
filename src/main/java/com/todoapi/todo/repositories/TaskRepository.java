package com.todoapi.todo.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import com.todoapi.todo.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Page<Task> findByUserId(Integer postId, Pageable pageable);
}
