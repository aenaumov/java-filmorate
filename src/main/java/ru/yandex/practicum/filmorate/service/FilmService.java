package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.ModelStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService extends ru.yandex.practicum.filmorate.service.Service<Film> {

    private final ModelStorage<User> userStorage;

    @Autowired
    public FilmService(ModelStorage<Film> modelStorage, ModelStorage<User> userStorage) {
        super(modelStorage);
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        if(!isUserId(userId)){
            return;
        }
        Film film = getModelById(filmId);
        film.addLike(userId);
        modelStorage.update(film);
    }

    public void deleteLike(Long filmId, Long userId) {
        if(!isUserId(userId)){
            return;
        }
        Film film = getModelById(filmId);
        film.deleteLike(userId);
        modelStorage.update(film);
    }

    public List<Film> getPopularFilms(Integer count) {
        return getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    private boolean isUserId(Long id) {
        Set<Long> allId = userStorage.getAllId();
        if (!allId.contains(id)) {
            throw new UserNotFoundException(String.format("User с id %d не найден", id));
        }
        return true;
    }
}
