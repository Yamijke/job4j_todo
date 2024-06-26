package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.category.CategoryService;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/task")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/task";
    }

    @GetMapping("/active")
    public String getPendingTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllPendingTasks());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/active";
    }

    @GetMapping("/completed")
    public String getCompletedTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllCompletedTasks());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/completed";
    }

    @PostMapping("/task/create")
    public String createNewTask(@ModelAttribute Task task, @RequestParam List<Integer> categoryIds, Model model, @SessionAttribute User user) {
        model.addAttribute("user", user);
        task.setUser(user);
        List<Category> categories = categoryService.findAllByIds(categoryIds);
        task.setParticipates(categories);
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
    public String update(@ModelAttribute Task task, Model model, @SessionAttribute User user) {
        model.addAttribute("user", user);
        task.setUser(user);
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Task with the specified identifier not found");
            return "errors/404";
        }
        return "redirect:/tasks/task";
    }

    @GetMapping("/task/delete/{id}")
    public String delete(Model model, @PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Failed to delete the task");
            return "errors/404";
        }
        return "redirect:/tasks/task";
    }

    @GetMapping("/task/complete/{id}")
    public String completeTask(Model model, @ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        var isUpdated = taskService.completeTask(task);
        if (!isUpdated) {
            model.addAttribute("message", "Failed to complete the task");
            return "errors/404";
        }
        return "redirect:/tasks/task";
    }
}
