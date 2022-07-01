package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.User;

public class UserNameValidation implements Validator<User> {
    @Override
    public void validate(User user) {
        final String name = user.getName();
        if (name.isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
