package ru.job4j.todo.repository.task;

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
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Удалить задание по id.
     *
     * @param id ID
     */
    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var query = session.createQuery(
                            "delete Task where id = :fId")
                    .setParameter("fId", id);
            int rsl = query.executeUpdate();
            session.getTransaction().commit();
            return rsl > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
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
            var query = session.createQuery("update Task set title = :fTitle, description = :fDescription, done = :fDone, created = :fCreated where id = :fId")
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fCreated", Timestamp.valueOf(task.getCreationDate()))
                    .setParameter("fId", task.getId());
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
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
            Optional<Task> rsl = session.createQuery("from Task where id = :ftaskId", Task.class)
                    .setParameter("ftaskId", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
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
            List<Task> rsl = session.createQuery("from Task order by id", Task.class).list();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    /**
     * Список заданий по статусу.
     *
     * @return список выполненных заданий.
     */
    @Override
    public List<Task> findAllPendingTasks() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Task> rsl = session.createQuery("from Task where done = false order by id", Task.class).list();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    /**
     * Список заданий по статусу.
     *
     * @return список выполненных заданий.
     */
    @Override
    public List<Task> findAllCompletedTasks() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<Task> rsl = session.createQuery("from Task where done = true order by id", Task.class).list();
            session.getTransaction().commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    /**
     * Обновить в базе задание с "в процессе" на "выполнено".
     *
     * @param task задание.
     */
    @Override
    public boolean completeTask(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var query = session.createQuery("update Task set done = :fDone where id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", task.getId());
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }
}
