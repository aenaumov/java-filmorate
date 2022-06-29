package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.exception.ValidateFilmRealiseDateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmRealiseDateValidator implements FilmValidator {
    final static LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    @Override
    public void validate(Film film) throws ValidateException {
        if (film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidateFilmRealiseDateException("Дата релиза фильма не может быть ранее " + CINEMA_BIRTHDAY);
        }

    }
}
