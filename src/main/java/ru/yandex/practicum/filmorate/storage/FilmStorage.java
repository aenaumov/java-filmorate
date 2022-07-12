package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

public interface FilmStorage {

    Collection<Film> getAll();

    Set<Integer> getAllId();

    void add(Film film);

    void update(Film film);

    void delete(Film film);
}
