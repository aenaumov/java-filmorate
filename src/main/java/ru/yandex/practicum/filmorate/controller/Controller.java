package ru.yandex.practicum.filmorate.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.validator.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Slf4j
public abstract class Controller {
    protected final HashMap<Integer, Model> models = new HashMap<>();

    @Setter
    protected List<Validator> validators;
    protected int id = 0;

    protected Collection<Model> getAll() {
        return models.values();
    }

    protected void post(Model model) throws ValidateException {

        checkValidation(model);

        model.setId(++id);
        models.put(id, model);
    }

    protected void put(Model model) throws ValidateException {

        final int id = model.getId();
        if (models.containsKey(id)) {
            checkValidation(model);
            models.replace(id, model);
        } else {
            throw new ValidateException("нет объекта с таким id: "+id);
        }
    }

    protected void checkValidation(Model model) throws ValidateException {
        for (Validator validator : validators) {
            validator.validate(model);
        }
    }

}
