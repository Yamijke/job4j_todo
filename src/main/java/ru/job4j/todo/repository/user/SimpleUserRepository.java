package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Repository
@AllArgsConstructor
public class SimpleUserRepository implements UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOGGER.error("A user with such login already exists" + e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
            var query = session.createQuery("from User u where u.login = :login and u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password);
            result = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public Collection<User> findAll() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            List<User> rsl = session.createQuery("from User", User.class).list();
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
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public boolean deleteById(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var query = session.createQuery(
                            "delete User where id = :fId")
                    .setParameter("fId", userId);
            int affectedRows = query.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }
}
