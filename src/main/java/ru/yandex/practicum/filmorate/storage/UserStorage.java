package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {
    Collection<User> getAll();

    Set<Integer> getAllId();

    void add(User user);

    void update(User user);

    void delete(User user);
}
