package com.jlim.taskmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // gives getters, setter, toString, equals and hash code method boiler plate
@NoArgsConstructor // creates constructor with no args
@AllArgsConstructor // apply these fields to the constructor
@Entity
@Table(name="tasks")
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(min=3, max = 100, message = "Title must be between 3 and 100 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean completed;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // protected method to set createdAt before persisting the entity
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        completed = false;
    }
}