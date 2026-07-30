package com.jlim.taskmanager.repository;

import com.jlim.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // method name query [Custom Query]
    // spring is smart enough to deduce the sql query from your method name

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContainingIgnoreCase(String title);

    // custom query that requires @Query annotation for Java Persistence Query Language (JPQL)
    // by querying the entity name
    @Query("SELECT t FROM Task t WHERE t.completed = :completed")
    List<Task> findTasksByCompletionStatus(@Param("completed") boolean completed);
}
