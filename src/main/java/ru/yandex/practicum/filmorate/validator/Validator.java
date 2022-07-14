package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.Model;

public interface Validator<T extends Model> {
    void validate(T model);
}
