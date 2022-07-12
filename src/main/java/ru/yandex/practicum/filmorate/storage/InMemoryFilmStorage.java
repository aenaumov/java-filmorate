package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    protected final HashMap<Integer, Film> models = new HashMap<>();
    protected static Integer idModel = 0;

    private static Integer getNextId() {
        return idModel++;
    }

    @Override
    public Collection<Film> getAll() {
        return models.values();
    }

    @Override
    public Set<Integer> getAllId() {
        return models.keySet();
    }

    @Override
    public void add(Film film) {
        film.setId(++idModel);
        models.put(idModel, film);
        log.debug("Создан объект: {}", film);
    }

    @Override
    public void update(Film film) {
        final Integer id = film.getId();
        models.replace(id, film);
        log.debug("Обновлен объект: {}", film);
    }

    @Override
    public void delete(Film film) {
        final Integer id = film.getId();
        models.remove(id, film);
        log.debug("Удален объект: {}", film);
    }
}
