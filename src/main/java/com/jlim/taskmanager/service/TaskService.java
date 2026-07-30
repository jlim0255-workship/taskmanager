package com.jlim.taskmanager.service;

import com.jlim.taskmanager.entity.Task;
import com.jlim.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // this sits between controller and repository, specify spring that db operations run in transactions. If something in the transaction fails, it rolls back
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    // move the business logic from controller to service layer, and call the service layer from the controller. This is a better design pattern,
    // as it separates concerns and makes the code more maintainable and testable.
    // CRUD
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id){
        // the return type can be null
        return taskRepository.findById(id);
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task updatedTask){
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.getCompleted());
                    return taskRepository.save(task);
                });
    }

    public boolean deleteTask(Long id){
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                }).orElse(false);
    }

    // -- Custom Endpoints
    public List<Task> getTasksByCompletionStatus(boolean completed){
        return taskRepository.findTasksByCompletionStatus(completed);
    }

    public List<Task> getTasksByTitle(String title){
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

}
