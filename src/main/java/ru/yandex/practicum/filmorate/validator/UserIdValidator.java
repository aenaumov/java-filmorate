package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.exception.ValidateUserIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Optional;

public class UserIdValidator implements Validator<User> {
    @Override
    public void validate(User user) throws ValidateException {
        Integer id = user.getId();
        Optional<Integer> idOptional = Optional.ofNullable(id);
        if (idOptional.isEmpty()) {
            return;
        }
        if (id < 0) {
            throw new ValidateUserIdException("id пользователя не может быть отрицательным");
        }

    }

}
