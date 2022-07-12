package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class User extends Model {

    @Email(regexp = "\\w+@\\w+.\\w+", message = ("email не прошел валидацию"))
    @NotNull
    private String email;

    @Pattern(regexp = "\\w+", message = ("login не может быть пустым или содержать пробелы"))
    @NotNull
    private String login;

    @NotNull
    private String name;

    @Past(message = "Дата рождения не может быть в будущем")
    @NotNull
    private LocalDate birthday;

    private Set<Integer> friends=new HashSet<>();

    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        super(id);
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addFriend(Integer friendId){
        friends.add(friendId);
    }

    public void deleteFriend(Integer friendId){
        friends.remove(friendId);
    }
}
