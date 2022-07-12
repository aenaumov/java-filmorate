package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.ModelStorage;
import ru.yandex.practicum.filmorate.validator.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService extends ru.yandex.practicum.filmorate.service.Service<User> {

    protected static final List<Validator<User>> validators = List.of(new UserNameValidation()
            , new UserIdValidator());

    public UserService(ModelStorage<User> modelStorage) {
        super(modelStorage, validators);
    }

    public boolean isUserId(Integer id) {
        Set<Integer> allId = modelStorage.getAllId();
        if (!allId.contains(id)) {
            throw new UserNotFoundException(String.format("User с id %d не найден", id));
        }
        return true;
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = getModelById(userId);
        User friend = getModelById(friendId);
        friend.addFriend(userId);
        modelStorage.update(friend);
        user.addFriend(friendId);
        modelStorage.update(user);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        User user = getModelById(userId);
        User friend = getModelById(friendId);
        friend.deleteFriend(userId);
        modelStorage.update(friend);
        user.deleteFriend(friendId);
        modelStorage.update(user);
    }

    public List<User> getListOfFriend(Integer id) {
        isUserId(id);
        return getAll().stream()
                .filter(x -> x.getFriends().contains(id))
                .collect(Collectors.toList());
    }

    public List<User> getListOfCommonFriends(Integer userId, Integer otherId) {
        User user = getModelById(userId);
        User other = getModelById(otherId);
        if (user.getFriends() == null || other.getFriends() == null) {
            return Collections.emptyList();
        }
        return getAll().stream()
                .filter(x -> x.getFriends().containsAll(List.of(userId, otherId)))
                .collect(Collectors.toList());
    }
}
