package com.todoapi.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapi.todo.repositories.*;
import com.todoapi.todo.domain.Task;
import com.todoapi.todo.exceptions.TaskNotFoundException;
import com.todoapi.todo.exceptions.UserNotFoundException;

@RestController
public class TaskController {
    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("api/tasks")
    public @ResponseBody Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("api/tasks/{id}")
    public @ResponseBody Task getTask(@PathVariable Integer id) {
        taskRepository.findAll().forEach(task -> {

        });
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @GetMapping("api/tasks/users/{userId}")
    public @ResponseBody Page<Task> getTasksByUser(@PathVariable Integer userId, Pageable pageable) {
        return taskRepository.findByUserId(userId, pageable);
    }

    @PostMapping("api/tasks/{userId}")
    public Task newTask(@PathVariable Integer userId, @RequestBody Task task) {
        return userRepository.findById(userId).map(user -> {
			task.setUser(user);
			return taskRepository.save(task);
		}).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PutMapping("api/tasks/{taskId}/users/{userId}")
    public Task replaceTask(@RequestBody Task newTask, @PathVariable Integer taskId,
                            @PathVariable Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
                                
        return taskRepository.findById(taskId).map(task -> {
            task.setDescription(newTask.getDescription());
            task.setDone(newTask.getDone());
            return taskRepository.save(task);
        }).orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @DeleteMapping("api/tasks/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskRepository.deleteById(id);
    }
}
