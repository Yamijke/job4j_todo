package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.service.SimpleTaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    SimpleTaskService simpleTaskService;

    public TaskController(SimpleTaskService simpleTaskService) {
        this.simpleTaskService = simpleTaskService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", simpleTaskService.findAll());
        return "task";
    }
}
