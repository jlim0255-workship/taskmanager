package com.jlim.taskmanager.mapper;

import com.jlim.taskmanager.dto.CategoryResponse;
import com.jlim.taskmanager.dto.TaskRequest;
import com.jlim.taskmanager.dto.TaskResponse;
import com.jlim.taskmanager.entity.Category;
import com.jlim.taskmanager.entity.Task;
import com.jlim.taskmanager.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final CategoryService categoryService;
    public TaskMapper(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    // Convert ResponseEntity to Task
    public Task toEntity(TaskRequest request){

        Category category = null;

        if(request.categoryId() != null){
            category = categoryService.findById(request.categoryId());
        }


        return Task.builder()
                .title(request.title())
                .description(request.description())
                .category(category)
                .completed(request.completed() != null ? request.completed() : false)
                .build();

    }

    // Convert Task to TaskResponse (ResponseEntity)
    public TaskResponse toResponse(Task task) {
        CategoryResponse categoryResponse = null;

        if (task.getCategory() != null) {
            categoryResponse = CategoryResponse.builder()
                    .categoryId(task.getCategory().getId())
                    .name(task.getCategory().getName())
                    .description(task.getCategory().getDescription())
                    .build();
        }

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .category(categoryResponse)
                .build();
    }

    public void updateEntityFromRequest(Task task, TaskRequest request){
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCompleted(request.completed());

        if(request.categoryId() != null){
            Category category = categoryService.findById(request.categoryId());
            task.setCategory(category);
        }
    }
}
