package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;

public interface UserValidator extends Validator {
    @Override
    default void validate(Model model) throws ValidateException {
        validator((User) model);
    }

    void validator(User user) throws ValidateException;
}
