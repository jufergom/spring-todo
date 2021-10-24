package com.todoapi.todo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.todoapi.todo.domain.*;

public interface UserRepository extends CrudRepository<User, Integer>  {
    
}