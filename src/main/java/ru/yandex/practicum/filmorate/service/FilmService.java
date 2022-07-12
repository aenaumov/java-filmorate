package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ModelStorage;
import ru.yandex.practicum.filmorate.validator.FilmIdValidator;
import ru.yandex.practicum.filmorate.validator.FilmRealiseDateValidator;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    protected static final List<Validator<Film>> validators = List.of(new FilmRealiseDateValidator()
            , new FilmIdValidator());

    private final ModelStorage<Film> filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(ModelStorage<Film> filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film add(Film model) throws ValidateException {
        checkValidation(model);
        filmStorage.add(model);
        return model;
    }

    public Film update(Film model) throws ValidateException {
        final Integer id = model.getId();
        if (filmStorage.getAllId().contains(id)) {
            checkValidation(model);
            filmStorage.update(model);
        } else {
            throw new FilmNotFoundException(String.format("Фильм c id %d не найден", id));
        }
        return model;
    }

    protected void checkValidation(Film film) throws ValidateException {
        for (Validator<Film> validator : validators) {
            validator.validate(film);
        }
    }

    public Film getFilmById(Integer id) {
        return getAll().stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new FilmNotFoundException(String.format("Фильм c id %d не найден", id)));
    }

    public void addLike(Integer filmId, Integer userId) {
        userService.isUserId(userId);
        Film film = getFilmById(filmId);
        film.addLike(userId);
        filmStorage.update(film);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        userService.isUserId(userId);
        Film film = getFilmById(filmId);
        film.deleteLike(userId);
        filmStorage.update(film);
    }

    public List<Film> getPopularFilms(Integer count) {
        return getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
