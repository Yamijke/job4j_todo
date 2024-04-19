package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.*;

import ru.job4j.todo.repository.CrudRepository;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Repository
@AllArgsConstructor
public class SimpleUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            LOGGER.error("A user with such login already exists" + e);
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
        return crudRepository.optional("from User u where u.login = :login and u.password = :password", User.class,
                Map.of("login", login,
                        "password", password)
        );
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public Collection<User> findAll() {
        return crudRepository.query("from User", User.class);
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public boolean deleteById(int userId) {
        try {
            crudRepository.run(
                    "delete User where id = :fId",
                    Map.of("fId", userId)
            );
            return true;
        } catch (Exception e) {
            LOGGER.error("Cant delete the User" + e);
        }
        return false;
    }
}
