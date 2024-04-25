package ru.job4j.todo.service.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.priority.SimplePriorityRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {
    SimplePriorityRepository simplePriorityRepository;

    @Override
    public List<Priority> findAll() {
        return simplePriorityRepository.findAll();
    }
}
