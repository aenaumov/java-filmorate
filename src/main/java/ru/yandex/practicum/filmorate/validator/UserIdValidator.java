package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.exception.ValidateUserIdException;
import ru.yandex.practicum.filmorate.model.User;

public class UserIdValidator implements UserValidator {
    @Override
    public void validate(User user) throws ValidateException {
        if (user.getId() < 0) {
            throw new ValidateUserIdException("id пользователя не может быть отрицательным");
        }
    }
}
