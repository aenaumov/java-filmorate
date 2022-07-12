package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.exception.ValidateFilmIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Optional;

public class FilmIdValidator implements Validator<Film> {
    @Override
    public void validate(Film film) throws ValidateException {

        Integer id = film.getId();
        Optional<Integer> idOptional = Optional.ofNullable(id);
        if (idOptional.isEmpty()) {
            return;
        }
        if (film.getId() < 0) {
            throw new ValidateFilmIdException("id фильма не может быть отрицательным");
        }
    }
}
