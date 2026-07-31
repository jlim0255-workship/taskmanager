package com.jlim.taskmanager.service;

import com.jlim.taskmanager.entity.Category;
import com.jlim.taskmanager.repository.CategoryRepository;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long Id){
        return categoryRepository.findById(Id).orElse(null);

    }

    public Category create(Category category){
        return categoryRepository.save(category);
    }
}
