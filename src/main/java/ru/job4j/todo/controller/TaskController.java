package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.SimpleTaskService;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("task", new Task());
        return "tasks/task";
    }

    @GetMapping("/active")
    public String getPendingTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllPendingTasks());
        return "tasks/active";
    }

    @GetMapping("/completed")
    public String getCompletedTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllCompletedTasks());
        return "tasks/completed";
    }

    @PostMapping("/task/create")
    public String createNewTask(@ModelAttribute Task task, Model model) {
        taskService.save(task);
        return "redirect:/tasks/task";
    }

    @GetMapping("/task/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Task with the specified identifier not found");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/taskDescription";
    }

    @PostMapping("/task/update")
    public String update(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Task with the specified identifier not found");
            return "errors/404";
        }
        return "redirect:/tasks/task";
    }

    @GetMapping("/task/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Failed to delete the task");
            return "errors/404";
        }
        return "redirect:/tasks/task";
    }

    @GetMapping("/task/complete/{id}")
    public String completeTask(Model model, @ModelAttribute Task task) {
        var isUpdated = taskService.completeTask(task);
        if (!isUpdated) {
            model.addAttribute("message", "Failed to complete the task");
            return "errors/404";
        }
        return "redirect:/tasks/task";
    }
}
