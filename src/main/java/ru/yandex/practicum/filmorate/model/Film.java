package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class Film extends Model {

    @NotBlank(message = ("название не может быть пустым"))
    @NotNull
    private String name;

    @Size(max = 200, message = ("длина описания должна быть менее 200 знаков"))
    @NotNull
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Positive(message = ("Продолжительность не может быть отрицательной"))
    @NotNull
    private Double duration;

    private Set<Integer> likes = new HashSet<>();

    public Film(Integer id, String name, String description, LocalDate releaseDate, Double duration) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void addLike(Integer userId) {
        likes.add(userId);
    }

    public void deleteLike(Integer userId) {
        likes.remove(userId);
    }
}
