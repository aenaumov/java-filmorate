package ru.yandex.practicum.filmorate.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.validator.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public abstract class Controller<T extends Model> {
    protected final HashMap<Integer, T> models = new HashMap<>();

    @Setter
    protected List<Validator> validators;
    protected int idModel = 0;


    @GetMapping
    public Collection<T> getAll() {
        return models.values();
    }

    @PostMapping
    public T post(@Valid @RequestBody T model) throws ValidateException {

        checkValidation(model);
        model.setId(++idModel);
        models.put(idModel, model);
        log.debug("Создан объект: {}", model);
        return model;
    }

    @PutMapping
    public T put(@Valid @RequestBody T model) throws ValidateException {

        final int id = model.getId();
        if (models.containsKey(id)) {
            checkValidation(model);
            models.replace(id, model);
            log.debug("Обновлен объект: {}", model);
        } else {
            throw new ValidateException("нет объекта с таким id: " + id);
        }
        return model;
    }

    protected void checkValidation(T model) throws ValidateException {
        for (Validator validator : validators) {
            validator.validate(model);
        }
    }

}
