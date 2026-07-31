package com.jlim.taskmanager.repository;

import com.jlim.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// maps a category class with a long
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
