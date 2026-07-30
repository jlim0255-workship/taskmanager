package com.jlim.taskmanager.mapper;

import com.jlim.taskmanager.dto.TaskRequest;
import com.jlim.taskmanager.dto.TaskResponse;
import com.jlim.taskmanager.entity.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    // Convert ResponseEntity to Task
    public Task toEntity(TaskRequest request){
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .completed(request.completed() != null ? request.completed() : false)
                .build();

    }

    // Convert Task to TaskResponse (ResponseEntity)
    public TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public void updateEntityFromRequest(Task task, TaskRequest request){
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCompleted(request.completed());
    }
}
