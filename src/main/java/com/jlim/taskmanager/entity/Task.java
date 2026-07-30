package com.jlim.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // gives getters, setter, toString, equals and hash code method boiler plate
@NoArgsConstructor // creates constructor with no args
@AllArgsConstructor // apply these fields to the constructor
public class Task {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
}