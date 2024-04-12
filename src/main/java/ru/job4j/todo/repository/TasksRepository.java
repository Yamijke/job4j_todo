package ru.job4j.todo.repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TasksRepository {

    Task save(Task task);

    void deleteById(int id);

    boolean update(Task task);

    Optional<Task> findById(int id);

    List<Task> findAll();
}
