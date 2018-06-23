package ru.itis.terziyan.diplom.entities.forms;

import ru.itis.terziyan.diplom.entities.User;

/**
 * Created by Vladislav Ulyanov on 23.05.17.
 * vk.com/etovladislav
 */
public class OutputMessage {

    private User from;

    private String message;

    private Long time;

    public OutputMessage(User from, String message, Long time) {
        this.from = from;
        this.message = message;
        this.time = time;
    }

    public OutputMessage() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
