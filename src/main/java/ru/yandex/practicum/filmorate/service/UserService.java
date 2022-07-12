package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    protected static final List<Validator<User>> validators = List.of(new UserNameValidation()
            , new UserIdValidator());

    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User add(User model) throws ValidateException {
        checkValidation(model);
        userStorage.add(model);
        return model;
    }

    public User update(User model) throws ValidateException {
        final Integer id = model.getId();
        if (userStorage.getAllId().contains(id)) {
            checkValidation(model);
            userStorage.update(model);
        } else {
            throw new UserNotFoundException(String.format("User с id %d не найден", id));
        }
        return model;
    }

    public void checkValidation(User user) throws ValidateException {
        for (Validator<User> validator : validators) {
            validator.validate(user);
        }
    }

    public User getUserById(Integer id) {
        return getAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("User с id %d не найден", id)));
    }

    public boolean isUserId(Integer id) {
        Set<Integer> allId = userStorage.getAllId();
        if (!allId.contains(id)) {
            throw new UserNotFoundException(String.format("User с id %d не найден", id));
        }
        return true;
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        friend.addFriend(userId);
        userStorage.update(friend);
        user.addFriend(friendId);
        userStorage.update(user);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        friend.deleteFriend(userId);
        userStorage.update(friend);
        user.deleteFriend(friendId);
        userStorage.update(user);
    }

    public List<User> getListOfFriend(Integer id) {
        isUserId(id);
        return getAll().stream()
                .filter(x -> x.getFriends().contains(id))
                .collect(Collectors.toList());
    }

    public List<User> getListOfCommonFriends(Integer userId, Integer otherId) {
        User user = getUserById(userId);
        User other = getUserById(otherId);
        if (user.getFriends() == null || other.getFriends() == null) {
            return Collections.emptyList();
        }
        return getAll().stream()
                .filter(x -> x.getFriends().containsAll(List.of(userId, otherId)))
                .collect(Collectors.toList());
    }
}
