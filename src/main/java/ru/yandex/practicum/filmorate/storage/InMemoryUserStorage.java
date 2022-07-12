package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;


@Component
public class InMemoryUserStorage extends InMemoryModelStorage<User> implements ModelStorage<User> {
}
