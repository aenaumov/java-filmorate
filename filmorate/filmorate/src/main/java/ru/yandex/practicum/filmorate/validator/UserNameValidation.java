package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.User;

public class UserNameValidation implements UserValidator {
    @Override
    public void validate(User user) {
        final String name = user.getName();
        if (name == null || name.isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
