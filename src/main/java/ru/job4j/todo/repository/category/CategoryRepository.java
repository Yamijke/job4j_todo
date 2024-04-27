package ru.job4j.todo.repository.category;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();

    List<Category> findById(List<Integer> ids);
}
