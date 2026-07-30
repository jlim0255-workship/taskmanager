package com.jlim.taskmanager.controller;

import com.jlim.taskmanager.entity.Task;
import com.jlim.taskmanager.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController // @Controller and @ResponseBody
@RequestMapping("/api/v1/tasks") //base url
public class TaskController {

    // constructor injection
    private TaskRepository taskRepository;

    public TaskController (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    // CRUD endpoints

    // -- Get
    @GetMapping("/{id}")
    public ResponseEntity<Task> getATaskById(@PathVariable Long id){
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    // -- Create
    // create task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // -- Update
    // can also use tasks.stream instead of for loop
    // send id and desired task request
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask){
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.getCompleted());
                    Task savedTask = taskRepository.save(task);

                    return ResponseEntity.ok(savedTask);
                })
                .orElse(ResponseEntity.notFound().build());

    }

    // -- Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
