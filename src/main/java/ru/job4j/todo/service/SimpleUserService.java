package ru.job4j.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public SimpleUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        return userRepository.deleteById(id);
    }
}
