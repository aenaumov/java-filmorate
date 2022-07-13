package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.ModelStorage;

import java.util.Collection;

public abstract class Service<T extends Model> {
    protected final ModelStorage<T> modelStorage;

    @Autowired
    public Service(ModelStorage<T> modelStorage) {
        this.modelStorage = modelStorage;
    }

    public Collection<T> getAll() {
        return modelStorage.getAll();
    }

    public T add(T model) throws ValidateException {
        modelStorage.add(model);
        return model;
    }

    public T update(T model) throws ValidateException {
        final Integer id = model.getId();
        if (modelStorage.getAllId().contains(id)) {
            modelStorage.update(model);
        } else {
            throw new UserNotFoundException(String.format("Объект с id %d не найден", id));
        }
        return model;
    }

    public T getModelById(Integer id) {
        return getAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("Объект с id %d не найден", id)));
    }

}
