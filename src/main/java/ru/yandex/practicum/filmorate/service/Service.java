package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.ModelStorage;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.Collection;
import java.util.List;

public abstract class Service<T extends Model> {
    protected final ModelStorage<T> modelStorage;

    protected final List<Validator<T>> validators;

    @Autowired
    public Service(ModelStorage<T> modelStorage, List<Validator<T>> validators) {
        this.modelStorage = modelStorage;
        this.validators = validators;
    }

    public Collection<T> getAll() {
        return modelStorage.getAll();
    }

    public T add(T model) throws ValidateException {
        checkValidation(model);
        modelStorage.add(model);
        return model;
    }

    public T update(T model) throws ValidateException {
        final Integer id = model.getId();
        if (modelStorage.getAllId().contains(id)) {
            checkValidation(model);
            modelStorage.update(model);
        } else {
            throw new UserNotFoundException(String.format("Объект с id %d не найден", id));
        }
        return model;
    }

    public void checkValidation(T model) throws ValidateException {
        for (Validator<T> validator : validators) {
            validator.validate(model);
        }
    }

    public T getModelById(Integer id) {
        return getAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("Объект с id %d не найден", id)));
    }

}
