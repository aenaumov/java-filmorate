package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    protected final HashMap<Integer, User> models = new HashMap<>();
    protected static Integer idModel = 0;

    private static Integer getNextId() {
        return idModel++;
    }

    @Override
    public Collection<User> getAll() {
        return models.values();
    }

    @Override
    public Set<Integer> getAllId() {
        return models.keySet();
    }

    @Override
    public void add(User user) {
        user.setId(++idModel);
        models.put(idModel, user);
        log.debug("Создан объект: {}", user);
    }

    @Override
    public void update(User user) {
        final Integer id = user.getId();
        models.replace(id, user);
        log.debug("Обновлен объект: {}", user);
    }

    @Override
    public void delete(User user) {
        final Integer id = user.getId();
        models.remove(id, user);
        log.debug("Удален объект: {}", user);
    }
}
