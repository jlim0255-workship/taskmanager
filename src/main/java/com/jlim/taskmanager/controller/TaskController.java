package com.jlim.taskmanager.controller;

import com.jlim.taskmanager.entity.Task;
import com.jlim.taskmanager.repository.TaskRepository;
import com.jlim.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller and @ResponseBody
@RequestMapping("/api/v1/tasks") //base url
public class TaskController {

    // constructor injection
//    private TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskController (TaskService taskService){

        this.taskService = taskService;
    }

    // CRUD endpoints

    // -- Get
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    // -- Create
    // create task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // -- Update
    // can also use tasks.stream instead of for loop
    // send id and desired task request
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask){
        return taskService.updateTask(id, updatedTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    // -- Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // -- Custom endpoint
    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletions(@PathVariable boolean status){
        return taskService.getTasksByCompletionStatus(status);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String title){
        return taskService.getTasksByTitle(title);
    }

}
