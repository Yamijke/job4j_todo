package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import org.hibernate.SessionFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskRepository implements TasksRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param task задание.
     * @return задание с id.
     */
    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return task;
    }

    /**
     * Удалить задание по id.
     *
     * @param id ID
     */
    @Override
    public void deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "delete Task where id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Обновить в базе задание.
     *
     * @param task задание.
     */
    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("update Task set description = :fDescription, created = :fCreated where id = :fId")
                    .setParameter("fDescription", "new description")
                    .setParameter("fCreated", Timestamp.valueOf(task.getCreationDate()))
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return false;
    }

    /**
     * Найти задание по ID
     *
     * @return задание.
     */
    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Optional<Task> rsl = session.createQuery("from Tasks where id = :ftaskId", Task.class)
                    .setParameter("ftaskId", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Optional.empty();
    }

    /**
     * Список заданий, отсортированных по id.
     *
     * @return список заданий.
     */
    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Task> rsl = session.createQuery("from Tasks order by id", Task.class).list();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return new ArrayList<>();
    }
}
