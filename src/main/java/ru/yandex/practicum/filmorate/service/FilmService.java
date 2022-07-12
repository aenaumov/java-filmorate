package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ModelStorage;
import ru.yandex.practicum.filmorate.validator.FilmIdValidator;
import ru.yandex.practicum.filmorate.validator.FilmRealiseDateValidator;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService extends ru.yandex.practicum.filmorate.service.Service<Film> {

    protected static final List<Validator<Film>> validators = List.of(new FilmRealiseDateValidator()
            , new FilmIdValidator());

    private final UserService userService;

    @Autowired
    public FilmService(ModelStorage<Film> modelStorage, UserService userService) {
        super(modelStorage, validators);
        this.userService = userService;
    }

    public void addLike(Integer filmId, Integer userId) {
        userService.isUserId(userId);
        Film film = getModelById(filmId);
        film.addLike(userId);
        modelStorage.update(film);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        userService.isUserId(userId);
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
}
