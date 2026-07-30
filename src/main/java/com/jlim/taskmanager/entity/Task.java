package com.jlim.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // gives getters, setter, toString, equals and hash code method boiler plate
@NoArgsConstructor // creates constructor with no args
@AllArgsConstructor // apply these fields to the constructor
@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

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