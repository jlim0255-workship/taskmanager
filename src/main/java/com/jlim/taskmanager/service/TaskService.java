package com.jlim.taskmanager.service;

import com.jlim.taskmanager.dto.TaskRequest;
import com.jlim.taskmanager.dto.TaskResponse;
import com.jlim.taskmanager.entity.Task;
import com.jlim.taskmanager.exception.TaskNotFoundException;
import com.jlim.taskmanager.mapper.TaskMapper;
import com.jlim.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // this sits between controller and repository, specify spring that db operations run in transactions. If something in the transaction fails, it rolls back
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    // move the business logic from controller to service layer, and call the service layer from the controller. This is a better design pattern,
    // as it separates concerns and makes the code more maintainable and testable.
    // CRUD
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public TaskResponse createTask(TaskRequest task){

        Task entityTask = taskMapper.toEntity(task);
        Task savedTask = taskRepository.save(entityTask);

        return taskMapper.toResponse(savedTask);

    }

    public TaskResponse updateTask(Long id, TaskRequest updatedTask){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        taskMapper.updateEntityFromRequest(task, updatedTask);
        taskRepository.save(task);
        return taskMapper.toResponse(task);

    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    // -- Custom Endpoints
    public List<Task> getTasksByCompletionStatus(boolean completed){
        return taskRepository.findTasksByCompletionStatus(completed);
    }

    public List<Task> getTasksByTitle(String title){
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

}
