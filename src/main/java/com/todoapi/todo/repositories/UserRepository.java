package com.todoapi.todo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.todoapi.todo.domain.User;

public interface UserRepository extends CrudRepository<User, Integer>  {
    
}