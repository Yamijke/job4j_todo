package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TasksRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    TasksRepository tasksRepository;

    @Override
    public Task save(Task task) {
        return tasksRepository.save(task);
    }

    @Override
    public void deleteById(int id) {
        tasksRepository.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return tasksRepository.update(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return tasksRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return tasksRepository.findAll();
    }

    @Override
    public List<Task> findAllPendingTasks() {
        return tasksRepository.findAllPendingTasks();
    }

    @Override
    public List<Task> findAllCompletedTasks() {
        return tasksRepository.findAllCompletedTasks();
    }
}