package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.exception.ValidateUserLoginException;
import ru.yandex.practicum.filmorate.model.User;

public class UserLoginValidator implements UserValidator {
    @Override
    public void validate(User user) throws ValidateException {
        final String login = user.getLogin();
        if (login.isEmpty()) {
            throw new ValidateUserLoginException("login не может быть пустым");
        }
        if (login.contains(" ")) {
            throw new ValidateUserLoginException("login не должен содержать пробелы");
        }
    }
}
