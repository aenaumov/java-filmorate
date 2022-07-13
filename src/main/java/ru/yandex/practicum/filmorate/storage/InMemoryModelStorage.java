package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Slf4j
public abstract class InMemoryModelStorage<T extends Model> implements ModelStorage<T> {

    protected final HashMap<Long, T> models = new HashMap<>();
    protected Long idModel = 0L;

    private Long getNextId() {
        return idModel++;
    }

    @Override
    public Collection<T> getAll() {
        return models.values();
    }

    @Override
    public Set<Long> getAllId() {
        return models.keySet();
    }

    @Override
    public void add(T model) {
        model.setId(++idModel);
        models.put(idModel, model);
        log.debug("Создан объект: {}", model);
    }

    @Override
    public void update(T model) {
        final Long id = model.getId();
        models.replace(id, model);
        log.debug("Обновлен объект: {}", model);
    }

    @Override
    public void delete(T model) {
        final Long id = model.getId();
        models.remove(id, model);
        log.debug("Удален объект: {}", model);
    }
}
