package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@RestController
public class FilmController extends Controller<Film> {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    @PostMapping("/films")
    public Film post(@Valid @RequestBody Film model) throws ValidateException {
        filmService.add(model);
        return model;
    }

    @PutMapping("/films")
    public Film put(@Valid @RequestBody Film model) throws ValidateException {
        return filmService.update(model);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable Integer id) {
        return filmService.getModelById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer filmId,
                        @PathVariable Integer userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer filmId,
                           @PathVariable Integer userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getListPopularFilms(@RequestParam(defaultValue = "10", required = false) Integer count) {
        if (count <= 0) {
            throw new IncorrectParameterException("count");
        }
        return filmService.getPopularFilms(count);
    }
}
