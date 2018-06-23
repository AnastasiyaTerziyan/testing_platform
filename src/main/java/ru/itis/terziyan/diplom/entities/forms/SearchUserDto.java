package ru.itis.terziyan.diplom.entities.forms;

import ru.itis.terziyan.diplom.entities.User;

/**
 * Created by Vladislav Ulyanov on 01.06.17.
 * vk.com/etovladislav
 */
public class SearchUserDto {
    Long id;

    String item;

    String value;

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SearchUserDto(Long id, String item, String value, User user) {

        this.id = id;
        this.item = item;
        this.value = value;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
