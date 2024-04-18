package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.job4j.todo.repository.CrudRepository;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Repository
@AllArgsConstructor
public class SimpleTaskRepository implements TasksRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param task задание.
     * @return задание с id.
     */
    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Удалить задание по id.
     *
     * @param id ID
     */
    @Override
    public boolean deleteById(int id) {
        boolean rsl;
        try {
            crudRepository.run(
                    "delete Task where id = :fId",
                    Map.of("fId", id)
            );
            rsl = true;
        } catch (Exception e) {
            LOGGER.error("Cant delete the task" + e);
            rsl = false;
        }
        return rsl;
    }

    /**
     * Обновить в базе задание.
     *
     * @param task задание.
     */
    @Override
    public boolean update(Task task) {
        boolean rsl;
        try {
            crudRepository.run(session -> session.merge(task));
            rsl = true;
        } catch (Exception e) {
            LOGGER.error("Cant update the task" + e);
            rsl = false;
        }
        return rsl;
    }

    /**
     * Найти задание по ID
     *
     * @return задание.
     */
    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "from Task where id = :ftaskId", Task.class,
                Map.of("ftaskId", id)
        );
    }

    /**
     * Список заданий, отсортированных по id.
     *
     * @return список заданий.
     */
    @Override
    public List<Task> findAll() {
        return crudRepository.query("from Task order by id", Task.class);
    }

    /**
     * Список заданий по статусу.
     *
     * @return список выполненных заданий.
     */
    @Override
    public List<Task> findAllPendingTasks() {
        return crudRepository.query("from Task where done = false order by id", Task.class);
    }

    /**
     * Список заданий по статусу.
     *
     * @return список выполненных заданий.
     */
    @Override
    public List<Task> findAllCompletedTasks() {
        return crudRepository.query("from Task where done = true order by id", Task.class);
    }

    /**
     * Обновить в базе задание с "в процессе" на "выполнено".
     *
     * @param task задание.
     */
    @Override
    public boolean completeTask(Task task) {
        boolean rsl;
        try {
            crudRepository.run("update Task set done = :fDone where id = :fId",
                    Map.of("fDone", true,
                            "fId", task.getId()));
            rsl = true;
        } catch (Exception e) {
            LOGGER.error("Cant complete the task" + e);
            rsl = false;
        }
        return rsl;
    }
}
