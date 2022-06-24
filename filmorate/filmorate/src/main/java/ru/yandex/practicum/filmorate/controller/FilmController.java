package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmRealiseDateValidator;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    HashMap<Integer, Film> films = new HashMap<>();
    private static final List<FilmValidator> validators = List.of(new FilmRealiseDateValidator());
    private int id = 0;

    @GetMapping
    public Collection<Film> getAll() {
        return films.values();
    }

    @PostMapping
    public Film post(@Valid @RequestBody Film film) {

        try {
            checkValidation(film);
        } catch (ValidateException e) {
            //TODO
            return null;
        }
        film.setId(++id);
        films.put(id, film);
        log.debug("Записан новый фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        try {
            checkValidation(film);
        } catch (ValidateException e) {
            //TODO
            return null;
        }
        final int id = film.getId();
        if (films.containsKey(id)) {
            films.replace(id, film);
            log.debug("Обновлен фильм: {}", film);
        } else {
            films.put(id, film);
            log.debug("Записан новый фильм: {}", film);
        }
        return film;
    }

    private static void checkValidation(Film film) throws ValidateException {
        for (FilmValidator validator : FilmController.validators) {
            validator.validate(film);
        }
    }
}
