package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmIdValidator;
import ru.yandex.practicum.filmorate.validator.FilmRealiseDateValidator;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {

    protected static final List<Validator> validators = List.of(new FilmRealiseDateValidator()
            , new FilmIdValidator());

    public FilmController() {
        super.setValidators(validators);
    }

}
