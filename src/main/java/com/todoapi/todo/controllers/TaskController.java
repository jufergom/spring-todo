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
public class TaskController {
    @Autowired
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("api/tasks")
    public @ResponseBody Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("api/tasks/{id}")
    public @ResponseBody Task getTask(@PathVariable Integer id) throws NotFoundException {
        return taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Task not found"));
    }

    @PostMapping("api/tasks")
    public Task newTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("api/tasks/{id}")
    public Task replaceTask(@RequestBody Task newTask, @PathVariable Integer id) {
        return taskRepository.findById(id)
            .map(task -> {
                task.setDescription(newTask.getDescription());
                task.setDone(newTask.getDone());
                return taskRepository.save(task);
            })
            .orElseGet(() -> {
                newTask.setId(id);
            return taskRepository.save(newTask);
        });
    }

    @DeleteMapping("api/tasks/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskRepository.deleteById(id);
    }
}
