package ru.job4j.todo.repository.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.CrudRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SimplePriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Priority> findAll() {
        return crudRepository.query("from Priority order by id", Priority.class);
    }
}
