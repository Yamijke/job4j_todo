package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.category.SimpleCategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {
    SimpleCategoryRepository simpleCategoryRepository;

    @Override
    public List<Category> findAll() {
        return simpleCategoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByIds(List<Integer> ids) {
        return simpleCategoryRepository.findById(ids);
    }
}
