package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;

import javax.validation.ValidationException;

public interface Validator {
    void validate(Model model) throws ValidationException, ValidateException;
}
