package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Model;

public interface FilmValidator extends Validator {
    @Override
    default void validate(Model model) throws ValidateException {
        validator((Film) model);
    }

    void validator(Film film) throws ValidateException;
}
