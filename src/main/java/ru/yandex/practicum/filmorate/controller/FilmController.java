package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.validator.FilmIdValidator;
import ru.yandex.practicum.filmorate.validator.FilmRealiseDateValidator;
import ru.yandex.practicum.filmorate.validator.Validator;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends Controller {

    protected static final List<Validator> validators = List.of(new FilmRealiseDateValidator()
            , new FilmIdValidator());

    public FilmController() {
        super.setValidators(validators);
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        Collection<Model> models = super.getAll();
        HashSet<Film> films = new HashSet<>();
        for (Model model : models) {
            films.add((Film) model);
        }
        return films;
    }

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) throws ValidateException {
        super.post(film);
        log.debug("Записан новый фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) throws ValidateException {
        super.put(film);
        log.debug("Обновлен фильм: {}", film);
        return film;
    }

}
