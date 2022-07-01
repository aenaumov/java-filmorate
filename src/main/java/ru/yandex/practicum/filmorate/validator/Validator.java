package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;

import javax.validation.ValidationException;

public interface Validator<T extends Model> {
    void validate(T model) throws ValidationException, ValidateException;
}
