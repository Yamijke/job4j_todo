package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.SimpleTaskService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    SimpleTaskService simpleTaskService;

    public TaskController(SimpleTaskService simpleTaskService) {
        this.simpleTaskService = simpleTaskService;
    }

    @GetMapping("/task")
    public String getAll(Model model) {
        model.addAttribute("tasks", simpleTaskService.findAll());
        return "tasks/task";
    }

    @GetMapping("/active")
    public String getPendingTasks(Model model) {
        model.addAttribute("tasks", simpleTaskService.findAllPendingTasks());
        return "tasks/active";
    }

    @GetMapping("/completed")
    public String getCompletedTasks(Model model) {
        model.addAttribute("tasks", simpleTaskService.findAllCompletedTasks());
        return "tasks/completed";
    }

    @PostMapping("/task")
    public String createNewTask(@ModelAttribute Task task, Model model) {
        try {
            simpleTaskService.save(task);
            return "redirect:/tasks/task";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}
