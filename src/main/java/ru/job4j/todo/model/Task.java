package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @Column(name = "created")
    private LocalDateTime creationDate = LocalDateTime.now();
    private boolean done;
}
