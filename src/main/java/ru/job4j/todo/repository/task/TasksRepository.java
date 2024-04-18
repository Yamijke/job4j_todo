package ru.job4j.todo.repository.task;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TasksRepository {

    Task save(Task task);

    boolean deleteById(int id);

    boolean update(Task task);

    Optional<Task> findById(int id);

    List<Task> findAll();

    List<Task> findAllPendingTasks();

    List<Task> findAllCompletedTasks();

    boolean completeTask(Task task);
}
