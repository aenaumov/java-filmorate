package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.exception.ValidateFilmIdException;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmIdValidator implements FilmValidator {
    @Override
    public void validator(Film film) throws ValidateException {
        if (film.getId() < 0) {
            throw new ValidateFilmIdException("id фильма не может быть отрицательным");
        }
    }
}
