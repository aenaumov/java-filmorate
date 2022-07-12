package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.Collection;
import java.util.Set;

public interface ModelStorage<T extends Model> {

    Collection<T> getAll();
    Set<Integer> getAllId();
    void add(T model);
    void update(T model);
    void delete(T model);
}
