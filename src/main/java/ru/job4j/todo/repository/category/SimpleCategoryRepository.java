package ru.job4j.todo.repository.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class SimpleCategoryRepository implements CategoryRepository {
    CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query("from Category order by name", Category.class);
    }

    @Override
    public List<Category> findById(List<Integer> ids) {
        Map<String, Object> categories = new HashMap<>();
        categories.put("ids", ids);
        return crudRepository.query("SELECT c FROM Category c WHERE c.id IN :ids", Category.class, categories);
    }
}
