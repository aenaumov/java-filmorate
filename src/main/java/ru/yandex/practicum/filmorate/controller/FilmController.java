package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
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

    /*
     * Получение всех film
     */
    @GetMapping
    public Collection<Film> getAll() {
        log.info("GET all films");
        return filmService.getAll();
    }

    /*
     * Добавление нового film
     */
    @PostMapping
    public Film post(@Valid @RequestBody Film model) {
        log.info("POST film {}", model);
        checkValidation(model);
        filmService.add(model);
        return model;
    }

    /*
     * Обновление film
     */
    @PutMapping
    public Film put(@Valid @RequestBody Film model) {
        log.info("PUT film {}", model);
        checkValidation(model);
        return filmService.update(model);
    }

    /*
     * Получение film по id
     */
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        log.info("GET film by id={}", id);
        return filmService.getModelById(id);
    }

    /*
     * Добавление лайка film от user
     */
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Long filmId,
                        @PathVariable Long userId) {
        log.info("PUT film id={} has been liked by user id={}", filmId, userId);
        filmService.addLike(filmId, userId);
    }

    /*
     * Удаление лайка film от user
     */
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Long filmId,
                           @PathVariable Long userId) {
        log.info("DELETE film id={} has been unliked by user id={}", filmId, userId);
        filmService.deleteLike(filmId, userId);
    }

    /*
     * Получение наиболее лайкнутых film
     */
    @GetMapping("/popular")
    public List<Film> getListPopularFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("GET {} more liked films", count);
        if (count <= 0) {
            throw new IncorrectParameterException("count");
        }
        return filmService.getPopularFilms(count);
    }

    /*
     * Проверка валидации film
     */
    public void checkValidation(Film model) {
        for (Validator<Film> validator : validators) {
            validator.validate(model);
        }
    }
}
