package com.todoapi.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

import com.todoapi.todo.repositories.*;
import com.todoapi.todo.domain.*;

@RestController
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("api/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("api/users/{id}")
    public @ResponseBody User getUser(@PathVariable Integer id) throws NotFoundException {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found"));
    }

    @PostMapping("api/users")
    public User newTask(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("api/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Integer id) {
        return userRepository.findById(id)
            .map(user -> {
                user.setEmail(newUser.getEmail());
                user.setName(newUser.getName());
                return userRepository.save(user);
            })
            .orElseGet(() -> {
                newUser.setId(id);
            return userRepository.save(newUser);
        });
    }

    @DeleteMapping("api/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}
