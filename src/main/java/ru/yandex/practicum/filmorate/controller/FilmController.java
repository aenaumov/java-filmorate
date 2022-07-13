package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validator.FilmIdValidator;
import ru.yandex.practicum.filmorate.validator.FilmRealiseDateValidator;
import ru.yandex.practicum.filmorate.validator.Validator;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private static final List<Validator<Film>> validators = List.of(new FilmRealiseDateValidator()
            , new FilmIdValidator());
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getAll() {
        log.info("GET all films");
        return filmService.getAll();
    }

    @PostMapping
    public Film post(@Valid @RequestBody Film model) throws ValidateException {
        log.info("POST film {}", model);
        checkValidation(model);
        filmService.add(model);
        return model;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film model) throws ValidateException {
        log.info("PUT film {}", model);
        checkValidation(model);
        return filmService.update(model);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) {
        log.info("GET film by id={}", id);
        return filmService.getModelById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer filmId,
                        @PathVariable Integer userId) {
        log.info("PUT film id={} has been liked by user id={}", filmId, userId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer filmId,
                           @PathVariable Integer userId) {
        log.info("DELETE film id={} has been unliked by user id={}", filmId, userId);
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getListPopularFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("GET {} more liked films", count);
        if (count <= 0) {
            throw new IncorrectParameterException("count");
        }
        return filmService.getPopularFilms(count);
    }

    public void checkValidation(Film model) throws ValidateException {
        for (Validator<Film> validator : validators) {
            validator.validate(model);
        }
    }
}
