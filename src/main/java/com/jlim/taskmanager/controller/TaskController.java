package com.jlim.taskmanager.controller;

import com.jlim.taskmanager.entity.Task;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController // @Controller and @ResponseBody
@RequestMapping("/api/v1/tasks") //base url
public class TaskController {

    // temporary in memory task list
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    // CRUD endpoints

    // -- Get
    @GetMapping("/{id}")
    public Task getATaskById(@PathVariable Long id){
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return tasks;
    }

    // -- Create
    // create task
    @PostMapping
    public Task createTask(@RequestBody Task task){
        task.setId(nextId++);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        tasks.add(task);

        return task;
    }

    // -- Update
    // can also use tasks.stream instead of for loop
    // send id and desired task request
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask){
        for (int i = 0; i < tasks.size(); i++){
            Task task = tasks.get(i);

            if (task.getId().equals(id)){
                updatedTask.setId(id);
                updatedTask.setCreatedAt(task.getCreatedAt());

                // replace the old task with new updated one
                tasks.set(i, updatedTask);
                return updatedTask;
            }

        }
        return null;

    }
    
    // -- Delete
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        tasks.removeIf(task -> task.getId().equals(id));
    }

}
