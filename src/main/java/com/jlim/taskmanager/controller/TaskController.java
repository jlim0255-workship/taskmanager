package com.jlim.taskmanager.controller;

import com.jlim.taskmanager.dto.TaskRequest;
import com.jlim.taskmanager.dto.TaskResponse;
import com.jlim.taskmanager.entity.Task;
import com.jlim.taskmanager.repository.TaskRepository;
import com.jlim.taskmanager.service.TaskService;
import jakarta.validation.Valid;
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
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    // -- Create
    // create task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest task){
        TaskResponse savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // -- Update
    // can also use tasks.stream instead of for loop
    // send id and desired task request
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest updatedTask){
        return taskService.updateTask(id, updatedTask);
    }

    // -- Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
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
