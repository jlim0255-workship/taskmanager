package com.jlim.taskmanager.controller;

import com.jlim.taskmanager.dto.TaskRequest;
import com.jlim.taskmanager.dto.TaskResponse;
import com.jlim.taskmanager.entity.Task;
import com.jlim.taskmanager.repository.TaskRepository;
import com.jlim.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ){
        // Implementation for paginated task retrieval
        Sort sort = sortDir.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // init page, size, sort into a Pageable object
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Task> taskPage = taskService.getAllTasks(pageable);

        List<TaskResponse> tasks = taskPage.getContent()
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getCompleted(),
                        task.getCreatedAt()
                )).toList();

        Map<String, Object> response = new HashMap<>();

        response.put("tasks", tasks);
        response.put("currentPage", taskPage.getNumber());
        response.put("totalItems", taskPage.getTotalElements());
        response.put("totalPages", taskPage.getTotalPages());
        response.put("hasNext", taskPage.hasNext());
        response.put("hasPrevious", taskPage.hasPrevious());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // -- search
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ){
        // Implementation for paginated task retrieval
        Sort sort = sortDir.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // init page, size, sort into a Pageable object
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Task> taskPage;

        if (title != null && completed != null) {
            // Filter by both
            taskPage = taskService.searchTasksByTitleAndCompletion(
                    title, completed, pageable
            );
        } else if (title != null) {
            // Filter by title only
            taskPage = taskService.searchTasksByTitle(title, pageable);
        } else if (completed != null) {
            // Filter completion only
            taskPage = taskService.getTasksByCompletion(completed, pageable);
        } else {
            taskPage = taskService.getAllTasks(pageable);
        }

        List<TaskResponse> tasks = taskPage.getContent()
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getCompleted(),
                        task.getCreatedAt()
                )).toList();

        Map<String, Object> response = new HashMap<>();

        response.put("tasks", tasks);
        response.put("currentPage", taskPage.getNumber());
        response.put("totalItems", taskPage.getTotalElements());
        response.put("totalPages", taskPage.getTotalPages());
        response.put("hasNext", taskPage.hasNext());
        response.put("hasPrevious", taskPage.hasPrevious());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    public List<TaskResponse> getTasksByCompletions(@PathVariable boolean status){
        return taskService.getTasksByCompletionStatus(status);
    }

    @GetMapping("/search")
    public List<TaskResponse> searchTasksByTitle(@RequestParam String title){
        return taskService.getTasksByTitle(title);
    }

}
